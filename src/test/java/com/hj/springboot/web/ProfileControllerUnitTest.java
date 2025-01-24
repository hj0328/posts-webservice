package com.hj.springboot.web;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;

class ProfileControllerUnitTest {

    @Test
    void real_profile이_조회된다() throws Exception {
        // given
        String expectedProfile = "real";
        MockEnvironment env = new MockEnvironment();
        env.addActiveProfile(expectedProfile);
        env.addActiveProfile("oauth");
        env.addActiveProfile("real_db");

        ProfileController profileController = new ProfileController(env);

        // when
        String profile = profileController.profile();

        // then
        Assertions.assertThat(profile).isEqualTo(expectedProfile);
    }

    @Test
    void real_profile이_없으면_첫번째가_조회() {
        // given
        String expectedProfile = "oauth";
        MockEnvironment env = new MockEnvironment();
        env.addActiveProfile(expectedProfile);
        env.addActiveProfile("real_db");

        ProfileController profileController = new ProfileController(env);

        // when
        String profile = profileController.profile();

        // then
        Assertions.assertThat(profile).isEqualTo(expectedProfile);
    }

    @Test
    void active_profile이_없으면_default가_조회() {
        // given
        String expectedProfile = "default";
        MockEnvironment env = new MockEnvironment();
        env.addActiveProfile(expectedProfile);
        ProfileController profileController = new ProfileController(env);

        // when
        String profile = profileController.profile();

        // then
        Assertions.assertThat(profile).isEqualTo(expectedProfile);
    }
}