package programs;

import com.battle.heroes.army.Army;
import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.GeneratePreset;

import java.util.*;

public class GeneratePresetImpl implements GeneratePreset {

    @Override
    public Army generate(List<Unit> unitList, int maxPoints) {
        Army army = new Army();
        Map<String, Integer> typeCount = new HashMap<>();
        int totalCost = 0;
        
        List<Unit> sorted = new ArrayList<>(unitList);
        sorted.sort((a, b) -> {
            double effA = (double) a.getBaseAttack() / a.getCost();
            double effB = (double) b.getBaseAttack() / b.getCost();
            return Double.compare(effB, effA);
        });
        
        int coordIndex = 0;
        boolean canAdd = true;
        
        while (canAdd && totalCost < maxPoints) {
            canAdd = false;
            
            for (Unit template : sorted) {
                int count = typeCount.getOrDefault(template.getUnitType(), 0);
                
                if (count < 11 && totalCost + template.getCost() <= maxPoints) {
                    int x = coordIndex / 21;
                    int y = coordIndex % 21;
                    
                    if (x < 3) {
                        Unit unit = new Unit(
                            template.getUnitType() + (count + 1),
                            template.getUnitType(),
                            template.getHealth(),
                            template.getBaseAttack(),
                            template.getCost(),
                            template.getAttackType(),
                            template.getAttackBonuses(),
                            template.getDefenceBonuses(),
                            x,
                            y
                        );
                        
                        army.getUnits().add(unit);
                        typeCount.put(template.getUnitType(), count + 1);
                        totalCost += template.getCost();
                        coordIndex++;
                        canAdd = true;
                    }
                }
            }
        }
        
        return army;
    }
}
