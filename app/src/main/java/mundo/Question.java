package mundo;

import java.io.Serializable;

/**
 * Created by Choringa on 22/04/15.
 */
public class Question implements Serializable{

    /**
     * Atributos de pregunta
     */
    private int idQuestion;
    private String question, answer1, answer2, answer3, answer4;
    private int user_type;

    /**
     * Constructor de la clase pregunta
     * @param idQuestion id de la pregunta
     * @param question la pregunta como tal
     * @param answer1 la primera ocpcion de respuesta
     * @param answer2 la segunda opcion de respuesta
     * @param answer3 la tercera opcion de respuesta
     * @param answer4 la cuarta opcion de respuesta
     * @param user_type el tipo de usuario para el cual la pregunta esta hecha
     */
    public Question(int idQuestion, String question, String answer1, String answer2, String answer3, String answer4, int user_type) {
        this.idQuestion = idQuestion;
        this.question = question;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.user_type = user_type;
    }

    //--------------------------
    // METODOS GETTER Y SETTER
    //--------------------------

    public int getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(int idQuestion) {
        this.idQuestion = idQuestion;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }

    public int getUser_type() {
        return user_type;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }

}
