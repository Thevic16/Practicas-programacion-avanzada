package edu.pucmm.eict;

import kong.unirest.GenericType;
import kong.unirest.Unirest;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        Boolean bucle = true;

        while(bucle){

            System.out.println(" ");
            System.out.println("Seleccione una opción (Un numero): ");
            System.out.println("(1) - Listar Estudiantes");
            System.out.println("(2) - Consultar un Estudiante");
            System.out.println("(3) - Crear un Estudiante");
            System.out.println("(4) - ELiminar un Estudiante");
            System.out.println("(5) - Actualizar un Estudiante");
            System.out.println("(6) - Salir");


            String stringMatricula = "";
            int matricula = 0;
            String nombre = "";
            String carrera = "";


            System.out.println(" ");
            System.out.println("Entre el opción: ");
            int caso = entrada.nextInt();
            entrada.nextLine();

            switch (caso){
                case 1:
                    ListarEstudiantes();
                    break;
                case 2:
                    System.out.println("Ingrese una matrícula:");
                    stringMatricula = entrada.nextLine();
                    consultarEstudiante(stringMatricula);
                    break;
                case 3:
                    System.out.println("Ingrese una matrícula:");
                    matricula = entrada.nextInt();
                    entrada.nextLine();

                    System.out.println("Ingrese un nombre:");
                    nombre = entrada.nextLine();

                    System.out.println("Ingrese un carrera:");
                    carrera = entrada.nextLine();

                    crearEstudiante(matricula,nombre,carrera);
                    break;
                case 4:
                    System.out.println("Ingrese una matrícula:");
                    stringMatricula = entrada.nextLine();
                    consultarEstudiante(stringMatricula);

                    eliminarEstudiante(stringMatricula);
                    break;
                case 5:
                    System.out.println("Ingrese una matrícula:");
                    matricula = entrada.nextInt();
                    entrada.nextLine();

                    System.out.println("Ingrese un nombre:");
                    nombre = entrada.nextLine();

                    System.out.println("Ingrese un carrera:");
                    carrera = entrada.nextLine();

                    actualizarEstudiante(matricula,nombre,carrera);
                    break;

                case 6:
                    bucle = false;
                    break;

                default:
                    System.out.println("Algo salio mal");
                    break;
            }
        }


    }


    public static void ListarEstudiantes (){
        List<Estudiante> listaEstudiante = Unirest.get("http://localhost:7000/api/estudiante/")
                .asObject(new GenericType<List<Estudiante>>() {})
                .getBody();
        System.out.println("");
        System.out.println("Listando estudiantes.");
        System.out.println("La cantidad de estudiante:"+listaEstudiante.size());

        for (Estudiante estudiante:listaEstudiante) {
            System.out.println("");
            System.out.println("Nombre: "+estudiante.getNombre());
            System.out.println("Carrera: "+estudiante.getCarrera());
            System.out.println("Matricula: "+estudiante.getMatricula());
        }
    }

    public static void consultarEstudiante(String matricula){
        Estudiante estudianteConsulta = Unirest.get("http://localhost:7000/api/estudiante/{matricula}")
                .routeParam("matricula", matricula)
                .asObject(new GenericType<Estudiante>() {})
                .getBody();

        System.out.println("");
        System.out.println("Consultando un estudiante.");
        System.out.println("Nombre: "+estudianteConsulta.getNombre());
        System.out.println("Carrera: "+estudianteConsulta.getCarrera());
        System.out.println("Matricula: "+estudianteConsulta.getMatricula());
    }

    public static void eliminarEstudiante(String matricula){
        Unirest.delete("http://localhost:7000/api/estudiante/{matricula}")
                .routeParam("matricula", matricula)
                .asObject(new GenericType<Estudiante>() {})
                .getBody();
    }


    public static void crearEstudiante(int matricula,String nombre, String carrera){
        Unirest.post("http://localhost:7000/api/estudiante/")
                .header("Content-Type", "application/json")
                .body(new Estudiante(matricula,nombre,carrera))
                .asEmpty();

        System.out.println("");
        System.out.println("Creando un estudiante.");

    }

    public static void actualizarEstudiante(int matricula,String nombre, String carrera){
        Unirest.put("http://localhost:7000/api/estudiante/")
                .header("Content-Type", "application/json")
                .body(new Estudiante(matricula,nombre,carrera))
                .asEmpty();

        System.out.println("");
        System.out.println("Actualizando un estudiante.");

    }



}


