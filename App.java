import java.util.Random;

public class App {
    public static void main(String[] args) {
        new App();
        Fighter fighter1 = new Cow(8, 100, 20, 60, "Susanne");
        Fighter fighter2 = new Beatle(8, 80, 20, -20, -60, "Seppl");
        for(int counter = 0; counter < 4; counter++){
            for(int counter2 = 0; counter2 < 2; counter2++){
                Fighter currentFighter;
                Fighter enemy;
                if(counter2 == 0){
                    currentFighter = fighter1;
                    enemy = fighter2;
                }else{
                    currentFighter = fighter2;
                    enemy = fighter1;
                }
                currentFighter.fight(enemy);
                currentFighter.eat();
                currentFighter.getHealth();
                System.out.printf("The current Fighter %S finished his turn.%n", currentFighter.getName());
            }
            System.out.printf("Round %d over%n", counter+1);
        }
        if(fighter1.getHealth() > fighter2.getHealth()){
            fighter1.getReward(3);
            System.out.printf("The Winner is: %s%n", fighter1.getName());
        }else if(fighter1.getHealth() < fighter2.getHealth()){
            fighter2.getReward(3);
            System.out.printf("The Winner is: %s%n", fighter2.getName());
        }else{
            System.out.println("DRAW");
        }
    }
        
}

abstract class Creature implements Fighter{
    int health;
    int maxHealth;
    int attackDamage;
    int x;
    int y;
    int speed;
    String name;
    int saturation = 10;
    int foodCount;
    Creature(int speed, int attackDamage, int x, int y, String name){
        maxHealth = 100;
        health = maxHealth;
        foodCount = 0;
        this.attackDamage = attackDamage;
        this.x = x;
        this.y = y;
        this.name = name;
    }
    public int getHealth(){
        return health;
    }
    public String getName(){
        return name;
    }
    public void getReward(int reward){
        foodCount += reward;
    }
    public void fight(Fighter enemy) {
        enemy.hurt(attackDamage);
    }
    public void hurt(int enemyAttackDamage){
        health -= enemyAttackDamage;
    }
    public void eat(){
        if(foodCount>0){
            foodCount--;
            if(health + saturation >= maxHealth){
                health = maxHealth;
            }else{
                health += saturation;
            }
            System.out.println("You've just eaten!");
        }else{
            System.out.println("No food remaining");
        }
    }
}

abstract class Insect extends Creature{
    int missingChance;
    Random random;
    Insect(int speed, int missingChance, int attackDamage, int x, int y, String name){
        super(speed, attackDamage, x, y, name);
        this.missingChance = missingChance;
    }
    public boolean missed(){
        random = new Random();
        boolean rückgabe = true;
        if(random.nextInt(99) >= missingChance){
            rückgabe = false;
        }
        return rückgabe;
    }
}

class Beatle extends Insect{
    int armor = 50;

    Beatle(int speed, int missingChance, int attackDamage, int x, int y, String name) {
        super(speed, missingChance, attackDamage, x, y, name);
    }
    @Override
    public void hurtse(int enemyAttackDamage){
        if(!super.missed()){
            if(armor > 0){  
                if((armor -= enemyAttackDamage) <= 0){
                    armor = 0;
                }else{
                    armor -= enemyAttackDamage;
                }
            }else{
                super.hurt(enemyAttackDamage);
            }
        }
        
    }
}

abstract class Säugetier extends Creature{
    
    Säugetier(int speed, int attackDamage, int x, int y, String name){
        super(speed*2, attackDamage, x, y, name);
    }
}

class Cow extends Säugetier{
    int euterStatus;
    int euterStatusMax = 400;
    Cow(int speed, int attackDamage, int x, int y, String name){
        super(speed, attackDamage, x, y, name);
        euterStatus = 0;
    }
    public void milchDrinken(){
        if(euterStatus > 100){
            euterStatus -= 100;
            attackDamage += 3;
        }
    }

    @Override
    public void eat(){
        super.eat(); 
        euterStatus += 50;
        milchDrinken();
    }
}

interface Fighter extends Eater{
    public void fight(Fighter enemy);
    
    public String getName();

    public void getReward(int i);

    public int getHealth();

    public void hurt(int enemyAttackDamage);
}
interface Eater{
    public void eat();
    }
    interface Runner{
    public void run();
}