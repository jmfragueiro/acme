package ar.com.acme.framework.utils.codes;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.zxing.WriterException;

import ar.com.acme.framework.common.Constantes;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(Constantes.SYS_CAD_CODES_URL)
@RequiredArgsConstructor
public class CodeController {
    private final ICodeService codeService;

    @GetMapping(path = "/imagen/{key}")
    public ResponseEntity<byte[]> getCodeImageByToken(@PathVariable("key") String token) throws IOException, WriterException {
        byte[] qrimagen = codeService.getQRImageByToken(token);

        return ResponseEntity
                .ok()
                .header("Content-Type", "image/png" + "; charset=UTF-8")
                .header("Content-Disposition", "attachment; filename=" + "imagenqr.png")
                .body(qrimagen);
    }
}
