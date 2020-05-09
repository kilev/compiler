package com.kil.view.editor;

import java.util.Optional;

public interface EditorService {

    void onTextChanged(String oldValue);

    Optional<String> getUndoText(String currentValue);

    String getRedoText();

    void saveToBuffer(String text);

    String getFromBuffer();
}
