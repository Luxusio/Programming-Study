package study.querydsl.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "username", "age"})
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String username;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    public static Member createMember(String username) {
        return createMember(username, 0);
    }

    public static Member createMember(String username, int age) {
        return createMember(username, age, null);
    }

    public static Member createMember(String username, int age, Team team) {
        Member member = new Member();
        member.username = username;
        member.age = age;
        if (team != null) {
            member.changeTeam(team);
        }

        return member;
    }

    private void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }

}
