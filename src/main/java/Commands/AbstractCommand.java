package Commands;

import CityData.City;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class AbstractCommand {
    protected CommandNames name;
    protected String specification;
    protected boolean mode;
    private String argument;
    private City city;

    @Override
    public AbstractCommand clone() {
        AbstractCommand clone = null;
        try {
            clone = (AbstractCommand) super.clone();
        } catch (CloneNotSupportedException e) {
        }
        // TODO: copy mutable state here, so the clone can't change the internals of the original
        return clone;

    }
}
