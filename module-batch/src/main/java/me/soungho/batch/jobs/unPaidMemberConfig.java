package me.soungho.batch.jobs;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.soungho.domain.member.Member;
import me.soungho.domain.member.MemberRepository;
import me.soungho.domain.member.MemberStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Configuration
public class unPaidMemberConfig {
    private MemberRepository memberRepository;

    @Bean
    public Job unPaidMemberJob(
            JobBuilderFactory jobBuilderFactory,
            Step unPaidMemberJobStep
    ) {
        log.info("********** This is unPaidMemberJob");
        return jobBuilderFactory.get("unPaidMemberJob")  // 1_1
//                .preventRestart()  // 1_2
                .start(unPaidMemberJobStep)  // 1_3
                .build();  // 1_4
    }

    @Bean
    public Step unPaidMemberJobStep(
            StepBuilderFactory stepBuilderFactory
    ) {
        log.info("********** This is unPaidMemberJobStep");
        return stepBuilderFactory.get("unPaidMemberJobStep")
                .<Member, Member> chunk(10)
                .reader(unPaidMemberReader())
                .processor(this.unPaidMemberProcessor())
                .writer(this.unPaidMemberWriter())
                .build();
    }

    @Bean
    @StepScope
    public ListItemReader<Member> unPaidMemberReader() {
        log.info("********** This is unPaidMemberReader");
        List<Member> activeMembers = memberRepository.findByStatusEquals(MemberStatus.ACTIVE);
        log.info("          - activeMember SIZE : " + activeMembers.size());
        return new ListItemReader<>(activeMembers);
    }

    public ItemProcessor<Member, Member> unPaidMemberProcessor() {
        return new ItemProcessor<Member, Member>() {  // 1
            @Override
            public Member process(Member member) throws Exception {
                log.info("********** This is unPaidMemberProcessor");
                return member.setStatusByUnPaid();  // 2
            }
        };
    }

    public ItemWriter<Member> unPaidMemberWriter() {
        log.info("********** This is unPaidMemberWriter");
        return ((List<? extends Member> memberList) ->
                memberRepository.saveAll(memberList));  // 1
    }
}