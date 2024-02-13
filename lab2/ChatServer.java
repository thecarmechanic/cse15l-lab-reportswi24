import java.io.IOException;
import java.net.URI;

class Handler implements URLHandler{
    //A String state that requests are copied to
    String messages = "";

    public String handleRequest(URI url){

        if(url.getPath().equals("/")){

            return messages;
            
        } else if(url.getPath().equals("/add-message")){


            String[] parameters = url.getQuery().split("[=&]");
            if(parameters[0].equals("s") && parameters[2].equals("user")){
                messages += String.format("%1$s: %2$s\n",parameters[3],parameters[1]);
                return messages;
            }

        }
        return "404 Not Found!";
    }

}

class ChatServer{
    public static void main(String[] args) throws IOException{
        if (args.length == 0){
            System.out.println("Missing port number!");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
