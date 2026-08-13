package parser;


/*Esta clase modela los atributos y metodos comunes a todos los distintos tipos de parser existentes en la aplicacion*/
public abstract class GeneralParser<T> {
    public abstract T parse(String path);
}