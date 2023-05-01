package Encomendas;

import Artigos.Artigos;
import org.mvel.MVEL;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.*;

public class Transportadora {
    static float valorExpedicaoPequena = 2.00f;
    static float valorExpedicaoMedia = 4.20f;
    static float getValorExpedicaoGrande = 6.00f;
    static float imposto = 0.23f;

    private static int counter=0;

    private int id;
    private String nome;
    private int nif;
    private String email;
    private ArrayList<Artigos> artigosAssociados;
    private float margemLucro;
    private TipoFormula tipoFormula;
    private String formula;

    public Transportadora(){
        counter++;
        this.id = counter;
        this.nome = "";
        this.nif = 0;
        this.email = "";
        this.artigosAssociados = new ArrayList<>();

        this.margemLucro = Math.round((1.01f + new Random().nextFloat() * (1.50f-1.01f)) * 100) / 100f; // numero aleatorio entre 1.01 e 1.50
        this.tipoFormula = TipoFormula.Default;
        this.formula = "";
    }

    public Transportadora(String nome, int nif, String email, ArrayList<Artigos> artigos, TipoFormula tipo, String formula){ // "" para default
        counter++;
        this.id = counter;
        this.nome = nome;
        this.nif = nif;
        this.email = email;
        this.artigosAssociados = new ArrayList<>(artigos);
        this.margemLucro = Math.round((1.01f + new Random().nextFloat() * (1.50f-1.01f)) * 100) / 100f; // numero aleatorio entre 1.01 e 1.50
        this.tipoFormula = tipo;
        this.formula = formula;
    }

    public Transportadora(Transportadora t){
        counter++;
        this.id = counter;
        this.nome = t.nome;
        this.nif = t.nif;
        this.email = t.email;
        this.artigosAssociados = new ArrayList<>(t.artigosAssociados);
        this.margemLucro = t.margemLucro;
        this.tipoFormula = t.tipoFormula;
        this.formula = t.formula;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNif() {
        return nif;
    }

    public void setNif(int nif) {
        this.nif = nif;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Artigos> getArtigosAssociados() {
        return new ArrayList<>(artigosAssociados);
    }

    public void setArtigosAssociados(ArrayList<Artigos> artigosAssociados) {
        this.artigosAssociados = new ArrayList<>(artigosAssociados);
    }

    public float getMargemLucro(){
        return this.margemLucro;
    }

    public void setMargemLucro(float margem){
        this.margemLucro = margem;
    }

    public TipoFormula getTipoFormula(){
        return this.tipoFormula;
    }

    public void setTipoFormula(TipoFormula t){
        this.tipoFormula = t;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }



    public float calculaFormula(Transportadora t, float preco){
        if(t.tipoFormula == TipoFormula.Default){
            return calculaFormulaDefualt(preco);
        } else{
            return  calculaFormulaCusomized(t.formula, preco);
        }
    }

    public float calculaFormulaDefualt(float preco){
        return (preco*(margemLucro)*(1+imposto));
    }

    public static float evaluateFormula(String formula, Map<String, Float> variables) {
        Object result = MVEL.eval(formula, variables);
        return Float.parseFloat(result.toString());
    }

    public float calculaFormulaCusomized(String line, float preco){
        Map<String, Float> variables = new HashMap<>();


        variables.put("preco", preco);
        variables.put("imposto", imposto);
        return evaluateFormula(line, variables);

    }

    @Override
    public int hashCode(){
        return Objects.hash(id, nome, nif, email, artigosAssociados, margemLucro, tipoFormula, formula);
    }

    public Transportadora clone(){
        return new Transportadora(this);
    }

    @Override
    public String toString(){
        return "Transportadora: " + nome + ";\nId: " + id + ";\nNif: " + nif + ";\nEmail: " + email + ";\nArtigos Associados: " + artigosAssociados +
                ";\nMargem de lucro: " + margemLucro + ";\nTipo de formula: " + tipoFormula + ";\nFormula: " + formula;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || o.getClass() != this.getClass()) return false;

        return Objects.equals(this.nome, ((Transportadora) o).nome) && this.id == ((Transportadora) o).id && this.nif == ((Transportadora) o).nif && Objects.equals(this.email, ((Transportadora) o).email)
                && this.artigosAssociados == ((Transportadora) o).artigosAssociados && this.margemLucro == ((Transportadora) o).margemLucro && this.tipoFormula == ((Transportadora) o).tipoFormula
                && Objects.equals(this.formula, ((Transportadora) o).formula);
    }
}