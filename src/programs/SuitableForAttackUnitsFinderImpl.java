package programs;

import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.SuitableForAttackUnitsFinder;

import java.util.ArrayList;
import java.util.List;

public class SuitableForAttackUnitsFinderImpl implements SuitableForAttackUnitsFinder {

    @Override
    public List<Unit> getSuitableUnits(List<List<Unit>> unitsByRow, boolean isLeftArmyTarget) {
        List<Unit> suitableUnits = new ArrayList<>();
        
        for (List<Unit> row : unitsByRow) {
            if (row == null || row.isEmpty()) {
                continue;
            }
            
            for (int col = 0; col < row.size(); col++) {
                Unit currentUnit = row.get(col);
                
                if (currentUnit == null || !currentUnit.isAlive()) {
                    continue;
                }
                
                boolean isSuitable = false;
                
                if (isLeftArmyTarget) {
                    boolean hasLeftNeighbor = (col > 0) && (row.get(col - 1) != null) && (row.get(col - 1).isAlive());
                    isSuitable = !hasLeftNeighbor;
                } else {
                    boolean hasRightNeighbor = (col < row.size() - 1) && (row.get(col + 1) != null) && (row.get(col + 1).isAlive());
                    isSuitable = !hasRightNeighbor;
                }
                
                if (isSuitable) {
                    suitableUnits.add(currentUnit);
                }
            }
        }
        
        return suitableUnits;
    }
}
