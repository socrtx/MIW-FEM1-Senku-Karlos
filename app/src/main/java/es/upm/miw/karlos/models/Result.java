package es.upm.miw.karlos.models;

/**
 * Created by Carlos Blanco Vaquerizo on 22/10/2017.
 */


public class Result {
    private String name;
    private int score;
    private String time;

    public Result(String name, int score, String time) {
        this.name = name;
        this.score = score;
        this.time = time;
    }

    public Result(String data) {
        String[] splitted = data.split("\t");
        this.name = splitted[0];
        this.score = Integer.parseInt(splitted[1]);
        this.time = splitted[2];
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String toString() {
        return this.name + "\t" + this.score + "\t" + this.time;
    }
}
