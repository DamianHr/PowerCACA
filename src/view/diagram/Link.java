package view.diagram;

import view.diagram.Anchor.LinkAnchor;

public class Link {
    private LinkAnchor firstAnchor;
    private LinkAnchor secondAnchor;

    public LinkAnchor getFirstAnchor() {
        return firstAnchor;
    }
    public void setFirstAnchor(LinkAnchor firstAnchor) {
        this.firstAnchor = firstAnchor;
    }

    public LinkAnchor getSecondAnchor() {
        return secondAnchor;
    }
    public void setSecondAnchor(LinkAnchor secondAnchor) {
        this.secondAnchor = secondAnchor;
    }

    public Link(LinkAnchor a1, LinkAnchor a2) {
        firstAnchor = a1;
        secondAnchor = a2;
    }
}
