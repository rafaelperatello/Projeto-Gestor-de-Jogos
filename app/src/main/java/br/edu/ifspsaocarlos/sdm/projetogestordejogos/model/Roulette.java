package br.edu.ifspsaocarlos.sdm.projetogestordejogos.model;

public class Roulette {
    private int number;
    private int backgroundColor;
    private int image;

    public Roulette(){
    }

    public Roulette(int number, int backgroundColor, int image){
        this.number = number;
        this.backgroundColor = backgroundColor;
        this.image = image;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
