package study.querydsl.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import study.querydsl.dto.MemberSearchCondition;
import study.querydsl.dto.MemberTeamDto;
import study.querydsl.dto.QMemberTeamDto;
import study.querydsl.entity.Member;
import study.querydsl.entity.QMember;
import study.querydsl.entity.QTeam;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.springframework.util.StringUtils.hasText;
import static study.querydsl.entity.QMember.member;
import static study.querydsl.entity.QTeam.team;

@Repository
@RequiredArgsConstructor
public class MemberJpaRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public void save(Member member) {
        em.persist(member);
    }

    public Optional<Member> findById(Long id) {
        Member findMember = em.find(Member.class, id);
        return Optional.ofNullable(findMember);
    }

    public List<Member> findAll() {
//        return em.createQuery("select m from Member m", Member.class)
//                .getResultList();
        return queryFactory
                .selectFrom(member)
                .fetch();
    }


    public List<Member> findByUsername(String username) {
//        return em.createQuery("select m from Member m where m.username = :username", Member.class)
//                .setParameter("username", username)
//                .getResultList();
        return queryFactory
                .selectFrom(member)
                .where(member.username.eq(username))
                .fetch();
    }

    public List<MemberTeamDto> findByBuilder(MemberSearchCondition condition) {
        return queryFactory
                .select(new QMemberTeamDto(
                        member.id,
                        member.username,
                        member.age,
                        team.id,
                        team.name))
                .from(member)
                .leftJoin(member.team, team)
                .where(usernameEq(condition.getUsername()),
                        teamNameEq(condition.getTeamName()),
                        userAgeGoe(condition.getAgeGoe()),
                        userAgeLoe(condition.getAgeLoe()))
                .fetch();
    }

    private BooleanExpression usernameEq(String username) {
        return !hasText(username) ? null :
                member.username.eq(username);
    }

    private BooleanExpression teamNameEq(String teamName) {
        return !hasText(teamName) ? null :
                team.name.eq(teamName);
    }

    private BooleanExpression userAgeGoe(Integer ageGoe) {
        return ageGoe == null ? null :
                member.age.goe(ageGoe);
    }

    private BooleanExpression userAgeLoe(Integer ageLoe) {
        return ageLoe == null ? null :
                member.age.loe(ageLoe);
    }

}
