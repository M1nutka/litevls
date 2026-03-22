package lite.vls;

public record VlsDismension(
    Integer length,
    Integer width,
    Integer height
) {
    public VlsDismension{
        if (length <= 0 || width <= 0 || height <= 0){
            throw new IllegalArgumentException("Dismension must positive");
        }
    }

    public int volume(){
        return length * width * height;
    }
}