package lite.vls;

public record VlsDismension(
    int length,
    int width,
    int heigth
) {
    public VlsDismension{
        if (length <= 0 || width <= 0 || heigth <= 0){
            throw new IllegalArgumentException("Dismension must positive");
        }
    }

    public int volume(){
        return length * width * heigth;
    }
}