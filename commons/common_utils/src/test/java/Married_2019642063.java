public class Married_2019642063 {
    private Long id ;

    public Married_2019642063() {
    }

    private int IdCard; //身份证号
    private String  address;
    private String phone ;
    private Married_2019642063 partner;//伴侣
    private String name ;
    private String sex ;
    private int  age ;
    private boolean isMarry;

    public Married_2019642063(Long id, int idCard, String address, String phone, Married_2019642063 partner, String name, String sex, int age, boolean isMarry) {
        this.id = id;
        IdCard = idCard;
        this.address = address;
        this.phone = phone;
        this.partner = partner;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.isMarry = isMarry;
    }

    public static void main(String[] args) {

        Married_2019642063 yang=new Married_2019642063(1l,  123456,"绝情谷","123",null,"杨过","男",201,false);
        Married_2019642063 xiaolongnv=new Married_2019642063(2l,  123456789,"绝情谷","1234",null,"小龙女","男",21,false);
        boolean married = yang.married(xiaolongnv);
        if(married){
            xiaolongnv.isMarry=true;//如果结婚成功更新小龙女婚姻信息 ；
            xiaolongnv.partner=yang;//伴侣设置杨过

        }
    }
    public boolean married(Married_2019642063 married2019642063){
        if(married2019642063.isMarry||this.isMarry){
            System.out.println("对象已经结婚 ，无法结婚 ！");
            return  false;
        }
        if(married2019642063.age!=this.age){
            //年龄相同才能结婚
            System.out.println(this.name+"与"+ married2019642063.name+"年龄不符结婚失败");
            return  false;
        }
        if(married2019642063.sex!=this.sex){
            System.out.println(this.name+"与"+ married2019642063.name+"性别不符结婚失败");
            return  false;
        }
        this.partner= married2019642063;
        this.isMarry=true ;
        System.out.println(this.name+"与"+ married2019642063.name+"结婚成功");
        return  true;


    }
}
