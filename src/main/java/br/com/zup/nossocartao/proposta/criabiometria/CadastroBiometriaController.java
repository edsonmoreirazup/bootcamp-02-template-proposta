package br.com.zup.nossocartao.proposta.criabiometria;

import br.com.zup.nossocartao.proposta.associacartao.CartaoRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CadastroBiometriaController {


    private CartaoRepository cartaoRepository;

    public CadastroBiometriaController(CartaoRepository cartaoRepository) {
        this.cartaoRepository = cartaoRepository;
    }

    @PostMapping()
    @Transactional
    public void criaBiometria(){}

}
