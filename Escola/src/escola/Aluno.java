package escola;

public class Aluno {
    
    private int mat;
    private String nome;
    private String rg;
    private String cpf;
    private String dataNasc;
    private String idade;
    private String telefone;
    private int turno;
    private String rua;
    private String bairro;
    private String numCasa;

    public Aluno() {
    }

    public Aluno(int mat, String nome, String rg, String cpf, String dataNasc, String idade, String telefone, int turno, String rua, String bairro, String numCasa) {
        this.mat = mat;
        this.nome = nome;
        this.rg = rg;
        this.cpf = cpf;
        this.dataNasc = dataNasc;
        this.idade = idade;
        this.telefone = telefone;
        this.turno = turno;
        this.rua = rua;
        this.bairro = bairro;
        this.numCasa = numCasa;
    }

    public int getMat() {
        return mat;
    }

    public void setMat(int mat) {
        this.mat = mat;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(String dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public int getTurno() {
        return turno;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getNumCasa() {
        return numCasa;
    }

    public void setNumCasa(String numCasa) {
        this.numCasa = numCasa;
    }



    @Override
    public String toString() {
        return "'" + nome + "', "
                + "'" + rg + "', "
                + "'" + cpf + "', "
                + "'" + dataNasc + "', "
                + "'" + telefone + "', "
                + "'" + turno + "', "
                + "'" + rua + "', "
                + "'" + bairro + "', "
                + "'" + numCasa + "'"
                ;
    }  
}