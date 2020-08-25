package familymap.client.Model;

import familymap.server.modelClasses.ModelEvents;

public class LinkEvents {
    private ModelEvents firstLoc;
    private ModelEvents secondLoc;

    public LinkEvents(ModelEvents first, ModelEvents second){
        setFirst(first);
        setSecond(second);
    }

    public ModelEvents getFirst() { return firstLoc; }
    public void setFirst(ModelEvents first) { this.firstLoc = first; }
    public ModelEvents getSecond() { return secondLoc; }
    public void setSecond(ModelEvents second) { this.secondLoc = second; }
}
