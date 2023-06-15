import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Consultorio {
    private static ArrayList<Usuario> usuario = new ArrayList<Usuario>();
    public static HashMap<Integer, Medico> listaMedicos = new HashMap<Integer, Medico>();
    Scanner Me = new Scanner(System.in);
    public static HashMap<Integer, Paciente> listaPacientes = new HashMap<Integer, Paciente>();
    Scanner Pa = new Scanner(System.in);

    public static HashMap<Integer, Cita> listaCitas = new HashMap<Integer, Cita>();
    Scanner Ci = new Scanner(System.in);
    private static Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {
        crearlistas (listaMedicos, listaPacientes, listaCitas);
        if (validarAcceso()) {
            System.out.println("Acceso Autorizado");
            menu();
        }
        else
            System.out.println("\nUsuario no autorizado.");

        System.out.println("Gracias por utilizar nuesta aplicacion");
    }
    public static void crearlistas (HashMap listaMedicos, HashMap listaPacientes, HashMap listaRece) {

        String inputFilenameMedicos = "src/BD/Medicos.cvs";
        String inputFilenamePacientes = "src/BD/Pacientes.cvs";
        String inputFilenameRecepcionista = "src/BD/Citas.cvs";
        BufferedReader bufferedReader = null;
        String Nombre = "";
        String Especialidad = "";
        String Funcioones = "";
        try {
            bufferedReader = new BufferedReader(new FileReader(inputFilenameMedicos));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                int coma = line.indexOf(',');
                Nombre = line.substring(0, coma);
                Especialidad = line.substring(coma+1, line.length());
                int id = listaMedicos.size();
                listaMedicos.put(id+1, new Medico(Nombre,Especialidad));
            }
        } catch(IOException e) {
            System.out.println("IOException catched while reading: " + e.getMessage());
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                System.out.println("IOException catched while closing: " + e.getMessage());
            }
        }

        try {
            bufferedReader = new BufferedReader(new FileReader(inputFilenamePacientes));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Nombre = line;
                int id = listaPacientes.size();
                listaPacientes.put(id+1, new Paciente(line));
            }
        } catch(IOException e) {
            System.out.println("IOException catched while reading: " + e.getMessage());
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                System.out.println("IOException catched while closing: " + e.getMessage());
            }
        }
        try {
            bufferedReader = new BufferedReader(new FileReader(inputFilenameRecepcionista));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                int coma = line.indexOf(',');
                Nombre = line.substring(0, coma);
                Funcioones = line.substring(coma+1, line.length());
                int id = listaMedicos.size();
                listaRece.put(id+1, new Recepcion (Nombre, Funcioones));
            }
        } catch(IOException e) {
            System.out.println("IOException catched while reading: " + e.getMessage());
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                System.out.println("IOException catched while closing: " + e.getMessage());
            }
        }
    }
    public static void list(HashMap Cita) {
        System.out.println("\n Agenda de Citas");
        for (Iterator<Map.Entry<String, Cita>> entries = Cita.entrySet().iterator(); entries.hasNext(); ) {
            Map.Entry<String, Cita> entry = entries.next();
            String output = String.format("%s :  %s:", entry.getKey(), entry.getValue());
            System.out.println(output);
        }
    }

    public static void saveMe(HashMap Medico){
        String outputFilename = "src/BD/Medicos.cvs";
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(outputFilename));
            for (Iterator<Map.Entry<Integer, Medico>> entries = Medico.entrySet().iterator(); entries.hasNext(); ) {
                Map.Entry<Integer, Medico> entry = entries.next();
                String output = String.format("%s , %s", entry.getKey(), entry.getValue() + "\r\n");
                bufferedWriter.write(output);
            }
        } catch(IOException e) {
            System.out.println("IOException catched while reading: " + e.getMessage());
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (IOException e) {
                System.out.println("IOException catched while closing: " + e.getMessage());
            }
        }
    }
    public static void saveCi(HashMap listaCitas){
        String outputFilename = "src/BD/Citas.cvs";
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(outputFilename));
            for (Iterator<Map.Entry<Integer, Cita>> entries = listaCitas.entrySet().iterator(); entries.hasNext(); ) {
                Map.Entry<Integer, Cita> entry = entries.next();
                String output = String.format("%s , %s", entry.getKey(), entry.getValue() + "\r\n");
                bufferedWriter.write(output);
            }
        } catch(IOException e) {
            System.out.println("IOException catched while reading: " + e.getMessage());
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (IOException e) {
                System.out.println("IOException catched while closing: " + e.getMessage());
            }
        }
    }
    public static void SavePa(HashMap listaPacientes){
        String outputFilename = "src/BD/Pacientes.cvs";
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(outputFilename));
            for (Iterator<Map.Entry<Integer, Paciente>> entries = listaPacientes.entrySet().iterator(); entries.hasNext(); ) {
                Map.Entry<Integer, Paciente> entry = entries.next();
                String output = String.format("%s , %s", entry.getKey(), entry.getValue() + "\r\n");
                bufferedWriter.write(output);
            }
        } catch(IOException e) {
            System.out.println("IOException catched while reading: " + e.getMessage());
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (IOException e) {
                System.out.println("IOException catched while closing: " + e.getMessage());
            }
        }
    }


    private static boolean validarAcceso( ) {

        usuario.add(new Usuario("Administrator", "1234"));
        usuario.add(new Usuario("Doctor", "1234"));
        usuario.add(new Usuario("Recepcion", "1234"));


        System.out.println("CLINICA AGENDA CITA");
        System.out.print("Nombre de usuario: ");
        String nombre = teclado.nextLine();
        System.out.print("Contraseña: ");
        String contraseña = teclado.nextLine();
        Usuario Administrator = new Usuario(nombre, contraseña);
        return usuario.contains(Administrator);
    }
    private static void menu(){
        Scanner sn = new Scanner(System.in);
        boolean salir = false;
        int opcion;

        while (!salir) {
            System.out.println("\n Seleccione la Opcion deseada");
            System.out.println("1. Alta Doctor");
            System.out.println("2. Alta Paciente");
            System.out.println("3. Alta Cita");
            System.out.println("4. Agenda de Citas");
            System.out.println("5. Salir");
            try {
                System.out.println("Escribe una de las opciones:");
                opcion = sn.nextInt();
                int id = 0;
                String nombre = "";
                switch (opcion) {
                    case 1:
                        String especialidad = "";

                        System.out.print("Nombre Doctor: ");
                        nombre = teclado.nextLine();
                        System.out.print("Especialidad: ");
                        especialidad = teclado.nextLine();
                        id = listaMedicos.size();
                        listaMedicos.put(id+1, new Medico(nombre,especialidad));
                        saveMe(listaMedicos);
                        break;
                    case 2:
                        System.out.print("Nombre Paciente: ");
                        nombre = teclado.nextLine();
                        id = listaPacientes.size();
                        listaPacientes.put(id+1, new Paciente(nombre));
                        SavePa(listaPacientes);
                        break;

                    case 3:
                        int medico;
                        int paciente;
                        String fecha;
                        String motivo;
                        boolean valid = false;
                        do{
                            System.out.println("AGENDA DE DOCTORES");
                            for (Iterator<Map.Entry<Integer, Medico>> entries = listaMedicos.entrySet().iterator(); entries.hasNext(); ) {
                                Map.Entry<Integer, Medico> entry = entries.next();
                                String output = String.format("%s. %s", entry.getKey(), entry.getValue());
                                System.out.println(output);
                            }
                            System.out.print("Seleccione Doctor: ");
                            medico = Integer.parseInt(teclado.nextLine());
                            valid = listaMedicos.containsKey(medico);
                        }while(valid != true);
                        valid = false;
                        do{
                            System.out.println("AGENDA DE PACIENTES");
                            for (Iterator<Map.Entry<Integer, Paciente>> entries = listaPacientes.entrySet().iterator(); entries.hasNext(); ) {
                                Map.Entry<Integer, Paciente> entry = entries.next();
                                String output = String.format("%s. %s", entry.getKey(), entry.getValue());
                                System.out.println(output);
                            }
                            System.out.print("Seleccione Paciente: ");
                            paciente = Integer.parseInt(teclado.nextLine());
                            valid = listaPacientes.containsKey(paciente);
                        }while(valid != true);
                        valid = false;
                        Date testDate = null;
                        do{
                            System.out.print("Seleccione Fecha: ");
                            System.out.println("Introduzca la fecha con formato yyyy-MM-ddHH:mm:ss");
                            Scanner sc = new Scanner(System.in);
                            fecha = sc.nextLine();
                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
                            String date = fecha;
                            try{
                                testDate = df.parse(date);
                                valid = true;
                                saveCi(listaCitas);


                            } catch (Exception e){ System.out.println("Formato invalido para Fecha intente de nuevo!!");}

                            if (df != null) {
                                if(!df.format(testDate).equals(date)) {
                                    System.out.println("Formato invalido para Fecha intente de nuevo!!");
                                } else{
                                    //System.out.println("valid date");
                                    valid = true;
                                }
                            }
                        }while(valid != true);
                        System.out.print("Motivo de consulta: ");
                        motivo = teclado.nextLine();
                        id = listaCitas.size();
                        String Medico = listaMedicos.get(medico).toString();
                        int coma = Medico.indexOf(':');
                        Medico = Medico.substring(coma+2, Medico.length()).toString();
                        coma = Medico.indexOf(':');
                        Medico = Medico.substring(0, coma).toString();
                        Medico = Medico.substring(0, Medico.length()-13);
                        String Paciente = listaPacientes.get(paciente).toString();
                        coma = Paciente.indexOf(':');
                        Paciente = Paciente.substring(coma+2, Paciente.length()).toString();
                        listaCitas.put(id+1, new Cita(Medico, Paciente, testDate.toString(), motivo));
                        System.out.print(listaCitas.get(id+1));
                        saveCi(listaCitas);
                        break;
                    case 4:
                        list(listaCitas);
                        break;
                    case 5:
                        salir = true;
                        break;
                    default:
                        System.out.println("Solo números entre 1 y 4");
                }
            } catch (InputMismatchException e) {
                System.out.println("Debes insertar un número");
                sn.next();
            }
        }
    }
}
