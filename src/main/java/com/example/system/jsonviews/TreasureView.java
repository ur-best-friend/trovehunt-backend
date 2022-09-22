package com.example.system.jsonviews;

public class TreasureView {
    // used on variables that shouldn't be serialized in any case
    public interface HiddenView {  }
    public interface MinimalView {  }
    public interface CompleteView extends MinimalView {  }
}
