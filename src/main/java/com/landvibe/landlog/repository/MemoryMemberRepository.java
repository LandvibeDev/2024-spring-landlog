package com.landvibe.landlog.repository;

import com.landvibe.landlog.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;
//디비 사용하지않는 메모리 기반 저장소.
@Repository
public class MemoryMemberRepository implements MemberRepository {

    private static Map<Long, Member> store = new HashMap<>(); // ID, 회원객체
    private static long sequence = 0L; //회원ID자동증가를 위한 변수

    @Override
    public Member save(Member member) { //새로운 회원 저장 sequence증가시켜 새로운 회원 ID생성
        member.setId(++sequence);
        store.put(member.getId(), member); // 해당 ID를 member객체에 설정
        return member; 
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }
    //주어진 ID에 해당하는 회원 찾기. 
    @Override
    public Optional<Member> findByName(String name) { //주어진 이름에 해당하는 회원 찾기
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }
    @Override
    public Optional<Member> findByEmail(String email){
        return store.values().stream()
                .filter(member -> member.getEmail().equals(email))
                .findAny();
    }
    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    } //저장된 모든회원 ArrayList로 변환하여 반환

    public void clearStore() {
        store.clear();
    } //store에 저장된 모든 회원 데이터 삭제
}
