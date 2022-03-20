package modelos;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Pessoa {
    @Column(name = "nome", length = 50, nullable = false, unique = true, columnDefinition = "char(50)")
    private String nome;

    @Column(name = "idade", nullable = false, columnDefinition = "tinyint")
    private byte idade;

    @Column(name = "sexo", length = 1, nullable = false, columnDefinition = "char(1)")
    private char sexo;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public byte getIdade() {
        return idade;
    }

    public void setIdade(byte idade) {
        this.idade = idade;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }
}
