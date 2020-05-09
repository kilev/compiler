package com.kil.view.editor;

import com.google.common.base.Strings;
import com.google.common.collect.Queues;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Deque;
import java.util.Objects;
import java.util.Optional;

public class TextEditorService implements EditorService {

    private static final int UNDO_REDO_BUFFER_SIZE = 10;

    private final Deque<TextState> undoStates = Queues.newArrayDeque();
    private final Deque<TextState> redoStates = Queues.newArrayDeque();

    private TextState textBuffer;

    @Override
    public void onTextChanged(String oldValue) {
        if (undoStates.size() <= UNDO_REDO_BUFFER_SIZE) {
            if (undoStates.isEmpty() || Objects.equals(undoStates.getFirst().getText(), oldValue)) {
                undoStates.addFirst(new TextState(oldValue));
            }
//            if (!undoStates.isEmpty() && !undoStates.getFirst().getText().equals(oldValue)) {
//                undoStates.addFirst(new TextState(Strings.nullToEmpty(oldValue)));
//            }
        }
    }

    @Override
    public Optional<String> getUndoText(String currentText) {
        TextState undoState = undoStates.pollFirst();

        if (undoState == null) {
            return Optional.empty();
        }
        return Optional.of(Strings.nullToEmpty(undoState.getText()));
    }

    @Override
    public String getRedoText() {
        return "";
    }

    @Override
    public void saveToBuffer(String text) {
        textBuffer = new TextState(text);
    }

    @Override
    public String getFromBuffer() {
        if (textBuffer == null) {
            return null;
        }
        return textBuffer.getText();
    }

    @RequiredArgsConstructor
    private static class TextState {
        @Getter
        private final String text;
    }

}
