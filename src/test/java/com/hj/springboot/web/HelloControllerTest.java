package com.hj.springboot.web;

import com.hj.springboot.web.dto.HelloResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@RunWith(SpringRunner.class)                      JUni4의 테스트를 위한 어노테이션
@ExtendWith(SpringExtension.class)                  // JUnit5의 테스트위한 어노테이션
@WebMvcTest(controllers = HelloController.class)    // controller 집중 테스트
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void hello_리턴() throws Exception {
        String hello = "hello";
        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));
    }

    @Test
    void 롬복_기능_테스트() {
        // given
        String name = "test";
        int amount = 100;

        // when
        HelloResponseDto dto = new HelloResponseDto(name, amount);

        // then
        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getAmount()).isEqualTo(amount);
    }

    /*
        api param을 MockHttpServletRequestBuilder 에서 설정가능
        jsonPath 응답 값을 필드별로 검증가능
     */
    @Test
    void helloDto_리턴() throws Exception {
        String name = "hello";
        int amount = 1000;
        mvc.perform(
                        get("/hello/dto")
                                .param("name", name)
                                .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));
    }
}
