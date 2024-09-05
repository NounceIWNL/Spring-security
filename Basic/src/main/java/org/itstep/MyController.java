package org.itstep;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
    @PreAuthorize("hasAuthority('write')")
    @GetMapping("/root")
    public String root() {
        return "root";
    }

    @PreAuthorize("hasAuthority('write', 'user')")
    @GetMapping("/user")
    public String user() {
        return "user";
    }
}
