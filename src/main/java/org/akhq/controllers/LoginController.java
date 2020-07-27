package org.akhq.controllers;

import io.micronaut.context.annotation.Requires;
import io.micronaut.core.util.StringUtils;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.config.SecurityConfigurationProperties;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.views.View;
import io.swagger.v3.oas.annotations.Hidden;
import org.akhq.configs.Oidc;

import javax.inject.Inject;
import java.util.Optional;

@Secured(SecurityRule.IS_ANONYMOUS)
@Requires(property = SecurityConfigurationProperties.PREFIX + ".enabled", value = StringUtils.TRUE)
@Controller
@Hidden
public class LoginController extends AbstractController {
    @Inject
    private Oidc oidc;

    @Get("${akhq.server.base-path:}/login")
    @View("login")
    public HttpResponse<?> login() {
        return HttpResponse
            .ok()
            .body(
                    templateData(
                            Optional.empty(),
                            "oidcEnabled",
                            oidc.isEnabled(),
                            "oidcProvider",
                            oidc.getProvider(),
                            "oidcLabel",
                            oidc.getLabel()
                    )
            );
    }

    @Get("${akhq.server.base-path:}/login/{failed:[a-zA-Z]+}")
    @View("login")
    public HttpResponse<?> loginFailed(Optional<String> failed) {
        return HttpResponse
            .ok()
            .body(templateData(Optional.empty(), "failed", failed));
    }
}
