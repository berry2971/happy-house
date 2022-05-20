package com.ssafy.happyhouse.mapper;

import com.ssafy.happyhouse.domain.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberMapperTest {

    @Autowired
    UserMapper userMapper;

    @Test
    void findById() throws Exception {
        Member member1 = new Member("id1", "pw1!", "name1", "01012345678", "서울", "동작", "여의도", null, null);
        Member member2 = new Member("id2", "pw2!", "name2", "01023456789", "부산", "동래", "안락", null, null);
        Member member3 = new Member("id3", "pw3!", "name3", "01034567890", "강원", "인제", "북", "원통", null);
        userMapper.save(member1);
        userMapper.save(member2);
        userMapper.save(member3);
        Member findMember1 = userMapper.findById("id1");
        Member findMember2 = userMapper.findById("id2");
        Member findMember3 = userMapper.findById("id3");
        assertThat(member1.getPw()).isEqualTo(findMember1.getPw());
        assertThat(member2.getPw()).isEqualTo(findMember2.getPw());
        assertThat(member3.getPw()).isEqualTo(findMember3.getPw());
    }

    @Test
    void save() throws Exception {
        Member member1 = new Member("id1", "pw1!", "name1", "01012345678", "서울", "동작", "여의도", null, null);
        Member member2 = new Member("id2", "pw2!", "name2", "01023456789", "부산", "동래", "안락", null, null);
        Member member3 = new Member("id3", "pw3!", "name3", "01034567890", "강원", "인제", "북", "원통", null);
        userMapper.save(member1);
        userMapper.save(member2);
        userMapper.save(member3);
        List<Member> members = userMapper.findAll();
        assertThat(members.size()).isEqualTo(3);
    }

    @Test
    void modifyUser() throws Exception {
        Member member = new Member("id1", "pw1!", "name1", "01012345678", "서울", "동작", "여의도", null, null);
        userMapper.save(member);
        member.setName("name2");
        member.setBmk_addr_lv4("village2");
        userMapper.modifyUser(member);

        Member findMember = userMapper.findById("id1");
        assertThat(findMember.getName()).isEqualTo("name2");
        assertThat(findMember.getBmk_addr_lv4()).isEqualTo("village2");
    }

}