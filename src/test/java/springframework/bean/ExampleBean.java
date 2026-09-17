package springframework.bean;

public class ExampleBean {

        public ExampleBean() {
            System.out.println("ExampleBean instance created.");
        }
        private String name;

        public void setName(String name) {
            this.name = name;
        }
        public String getName() {
            System.out.println("Getting name: " + name);
            return name;
        }

}
