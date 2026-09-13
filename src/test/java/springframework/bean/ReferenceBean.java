package springframework.bean;

public class ReferenceBean {
    private ReferencedBean referencedBean;

    public void setReferencedBean(ReferencedBean referencedBean) {
        this.referencedBean = referencedBean;
    }

    public ReferencedBean getReferencedBean() {
        return referencedBean;
    }

    public static class ReferencedBean {
        private String name;
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}

