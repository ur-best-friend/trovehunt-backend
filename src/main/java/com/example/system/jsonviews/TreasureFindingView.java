package com.example.system.jsonviews;

public class TreasureFindingView {
    public interface HiddenView {  }
    public interface MinimalView {  }
    public interface CompleteView extends TreasureView.MinimalView {  }
}
