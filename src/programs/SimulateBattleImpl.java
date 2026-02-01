package programs;

import com.battle.heroes.army.Army;
import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.PrintBattleLog;
import com.battle.heroes.army.programs.SimulateBattle;

import java.util.ArrayList;
import java.util.List;

public class SimulateBattleImpl implements SimulateBattle {
    private PrintBattleLog printBattleLog;
    private static final int MAX_ROUNDS = 1000;

    public void setPrintBattleLog(PrintBattleLog printBattleLog) {
        this.printBattleLog = printBattleLog;
    }

    @Override
    public void simulate(Army playerArmy, Army computerArmy) throws InterruptedException {
        int roundCounter = 0;
        
        while (true) {
            roundCounter++;
            
            if (roundCounter > MAX_ROUNDS) {
                break;
            }
            
            List<Unit> aliveUnits = new ArrayList<>();
            
            List<Unit> playerUnits = playerArmy.getUnits();
            if (playerUnits != null) {
                for (Unit unit : playerUnits) {
                    if (unit != null && unit.isAlive()) {
                        aliveUnits.add(unit);
                    }
                }
            }
            
            List<Unit> computerUnits = computerArmy.getUnits();
            if (computerUnits != null) {
                for (Unit unit : computerUnits) {
                    if (unit != null && unit.isAlive()) {
                        aliveUnits.add(unit);
                    }
                }
            }
            
            boolean playerHasAlive = hasAliveUnits(playerArmy);
            boolean computerHasAlive = hasAliveUnits(computerArmy);
            
            if (!playerHasAlive || !computerHasAlive) {
                break;
            }
            
            if (aliveUnits.isEmpty()) {
                break;
            }
            
            aliveUnits.sort((u1, u2) -> Integer.compare(u2.getBaseAttack(), u1.getBaseAttack()));
            
            for (Unit attacker : aliveUnits) {
                if (!attacker.isAlive()) {
                    continue;
                }
                
                Unit target = attacker.getProgram().attack();
                
                if (target != null) {
                    if (printBattleLog != null) {
                        printBattleLog.printBattleLog(attacker, target);
                    }
                }
                
                playerHasAlive = hasAliveUnits(playerArmy);
                computerHasAlive = hasAliveUnits(computerArmy);
                
                if (!playerHasAlive || !computerHasAlive) {
                }
        }
    }
    
    private boolean hasAliveUnits(Army army) {
        List<Unit> units = army.getUnits();
        if (units == null || units.isEmpty()) {
            return false;
        }
        
        for (Unit unit : units) {
            if (unit != null && unit.isAlive()) {
                return true;
            }
        }
        
        return false;
    }
}