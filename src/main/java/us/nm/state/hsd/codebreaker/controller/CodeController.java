package us.nm.state.hsd.codebreaker.controller;

import java.net.URI;
import javax.validation.Valid;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import us.nm.state.hsd.codebreaker.CodebreakerServiceApplication;
import us.nm.state.hsd.codebreaker.model.entity.Code;
import us.nm.state.hsd.codebreaker.service.CodeService;

@RestController
@RequestMapping("/codes")
public class CodeController {

  private final CodeService codeService;

  public CodeController(CodeService codeService) {
    this.codeService = codeService;
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Code> post(@Valid @RequestBody Code code) {
    code = codeService.add(code);
    URI location = WebMvcLinkBuilder
        .linkTo(
            WebMvcLinkBuilder.methodOn(CodeController.class)
            .get(code.getKey())
            )
            .toUri();
    return ResponseEntity.created(location).body(code);
  }
  
  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Code get(@PathVariable String id) {
    return null;
  }
}
