class Java_CallByValue {
    // global static object
    private static String name = "Manish";

    public static void main(String[] args) {
        System.out.println("----------------------Global Static ----------------------");
        // global static object
        String nameans = methodTest(name);
        System.out.println("HelloWorld.name : " + name + " | methodTest.nameans: " + nameans);


        System.out.println("----------------------Immutable Object-String----------------------");
        // Immutable Object
        String temp = "temp";
        String tempans = methodTestString(temp);
        System.out.println("temp : " + temp + " | [i] : " + tempans);


        System.out.println("----------------------Wrapper Object-Integer----------------------");
        //Wrapper Object
        Integer j = 0;
        Integer jans = methodTestInteger(j);
        System.out.println("j : " + j + " | [j] : " + jans);

        System.out.println("----------------------Primitive Object----------------------");
        // primitive Object
        int i = 0;
        int ians = methodTestInt(i);
        System.out.println("i : " + i + " | [i] : " + ians);

        System.out.println("----------------------Mutable Object ----------------------");
        Student std = new Student(101, "Manish");
        System.out.println("Student : " + std.getId() + "_" + std.getName() + "_" + std.getColledge());
        Student stdans = methodTestObject(std);
        System.out.println("Student : " + std.getId() + "_" + std.getName() + "_" + std.getColledge() + " | [Student]: " + stdans.getId() + "_" + stdans.getName() + "_" + stdans.getColledge());


    }

    public static String methodTest(String nameAns) {
        System.out.println("HelloWorld.name : " + name + " | methodTest.name: " + nameAns);

        name = "Luste";
        System.out.println("HelloWorld.name : " + name + " | methodTest.name: " + nameAns);

        nameAns = "Manish D. Luste";

        System.out.println("HelloWorld.name : " + name + " | methodTest.name: " + nameAns);


        return nameAns;
    }

    public static int methodTestInt(int i) {
        System.out.println("[i] : " + i);
        i = i + 10;
        System.out.println("[i] : " + i);
        return i;
    }

    public static Integer methodTestInteger(Integer j) {
        System.out.println("[j] : " + j);
        j = j + 10;
        System.out.println("[j] : " + j);
        return j;
    }

    public static String methodTestString(String temp) {
        System.out.println("temp : " + temp);
        temp = temp + "-123";
        System.out.println("[temp] : " + temp);
        return temp;
    }

    public static Student methodTestObject(Student std) {
        std.setId(102);
        std.setName("Manish D.Luste");
        std.setColledge("IIT-Kanpur");
        return std;
    }


    public static class Student {
        private Integer id;
        private String name;
        private static String colledge = "IIT-Delhi";
        Student() {};

        Student(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

        public Integer getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public static String getColledge() {
            return colledge;
        }

        public static void setColledge(String colledge) {
            Student.colledge = colledge;
        }
    }
}