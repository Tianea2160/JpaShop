package jpagroup.jpashop.api;


import jpagroup.jpashop.domain.Member;
import jpagroup.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    //entity를 controller 단에서 사용하는 걸 자제 해야한다.
    @PostMapping("/api/v1/members")
    public CreateMemberResponse savedMemberV1(@RequestBody @Valid Member member){
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);   
    }

    // api 스펙이 고정된다.
    @PostMapping("/api/v2/members")
    public CreateMemberResponse savedMemeberV2(@RequestBody @Valid CreateMemberRequest request){
        Member member = Member.builder()
                .name(request.getName())
                .build();
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMember(
            @PathVariable("id")Long memberId,
            @RequestBody @Valid UpdateMemberRequest request){

        Long id = memberService.update(memberId, request.getName());
        Member findOne = memberService.findOne(id);
        return new UpdateMemberResponse(findOne.getId(),findOne.getName());
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse{
        private Long id;
        private String name;
    }

    @Data
    static class UpdateMemberRequest{
        @NotEmpty
        private String name;
    }

    @Data
    static class CreateMemberRequest{
        private String name;
    }

    @Data
    static class CreateMemberResponse{
        private Long id;
        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }
}
