package ru.bonsystems.appkode.ui;

import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;
import ru.bonsystems.swing.*;
import ru.bonsystems.swing.Button;
import ru.bonsystems.swing.utils.Wrapper;

import javax.swing.*;
import java.awt.*;
import java.awt.print.Book;
import java.util.concurrent.Callable;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Created by Kolomeytsev Anton on 02 Май 2016.
 * This class is a part of StringsTranslator project.
 */
public class LayoutHolder {

    public static LinearLayout createLayout() {
        return new Wrapper<>(LinearLayout.class).accept(container -> {
            container.setOrientation(LinearLayout.Orientation.HORIZONTAL);
            container.setGravity(LinearLayout.GravityX.CENTER, LinearLayout.GravityY.CENTER);
        });
    }

    public static Well getWellSelection(Runnable stringsToCsvListener, Runnable csvToStringsListener) {
        return new Wrapper<>(Well.class).accept(well -> {
            well.setSize(300, 125);
            well.setLayout(null);
            well.add(new Wrapper<>(LinearLayout.class).accept(c1 -> {
                c1.setOrientation(LinearLayout.Orientation.VERTICAL);
                c1.setGravityX(LinearLayout.GravityX.CENTER);
                c1.setLocation(0, 25);
                c1.addAll(
                        new Button("Strings to CSV", new Button.ThemeTealButton()).setOnClickListener(stringsToCsvListener::run),
                        new Button("CSV to Strings", new Button.ThemeRaisedButton()).setOnClickListener(csvToStringsListener::run)
                );
                c1.setSize(300, 100);
            }));
        });
    }

    public static Well getWellStringsToCsv(
            Callable<String> browseAndroidStrings,
            Callable<String> browseIosStrings,
            BiConsumer<String, String> mergeTo,
            Runnable back
    ) {
        return new Wrapper<>(Well.class).accept(layout2 -> {
            layout2.setSize(430, 225);
            layout2.setLayout(null);
            layout2.add(new Wrapper<>(LinearLayout.class).accept(c2 -> {
                c2.setOrientation(LinearLayout.Orientation.VERTICAL);
                c2.setGravityX(LinearLayout.GravityX.LEFT);
                c2.setLocation(25, 25);
                c2.setSize(430, 300);
                final EditText[] ios = new EditText[1];
                final EditText[] android = new EditText[1];
                c2.addAll(
                        new TextView("Path to Android strings"),
                        new Wrapper<>(LinearLayout.class).accept(l1 -> {
                            l1.setOrientation(LinearLayout.Orientation.HORIZONTAL);
                            l1.setGravity(LinearLayout.GravityX.LEFT, LinearLayout.GravityY.CENTER);
                            EditText editTextAndroid = new EditText();
                            android[0] = editTextAndroid;
                            editTextAndroid.setSize(300, 30);
                            l1.addAll(editTextAndroid, new Button("Browse", new Button.ThemeRaisedButton())
                                .setOnClickListener(() -> {
                                    try {
                                        String callResult = browseAndroidStrings.call();
                                        if (callResult != null) android[0].setText(callResult);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }));
                        }),
                        new TextView("Path to IOS strings"),
                        new Wrapper<>(LinearLayout.class).accept(l1 -> {
                            l1.setOrientation(LinearLayout.Orientation.HORIZONTAL);
                            l1.setGravity(LinearLayout.GravityX.LEFT, LinearLayout.GravityY.CENTER);
                            EditText editTextIos = new EditText();
                            ios[0] = editTextIos;
                            editTextIos.setSize(300, 30);
                            l1.addAll(editTextIos, new Button("Browse", new Button.ThemeRaisedButton())
                                .setOnClickListener(() -> {
                                    try {
                                        String callResult = browseIosStrings.call();
                                        if (callResult != null) ios[0].setText(callResult);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }));
                        }),
                        new Wrapper<>(LinearLayout.class).accept(l1 -> {
                            l1.setOrientation(LinearLayout.Orientation.HORIZONTAL);
                            l1.setGravity(LinearLayout.GravityX.RIGHT, LinearLayout.GravityY.CENTER);
                            JPanel offset = new JPanel();
                            offset.setSize(38, 64);
                            offset.setOpaque(false);
                            l1.addAll(
                                    offset,
                                    new Button("Merge to CSV...", new Button.ThemeTealButton()).setOnClickListener(
                                            () -> mergeTo.accept(android[0].getText(), ios[0].getText())),
                                    new Button("Back").setOnClickListener(back::run)
                            );
                        })
                );
            }));
        });
    }

    public static Well getWellCsvToStrings(
            Callable<String> browseCsvStrings,
            BiConsumer<String, Pair<Boolean, Boolean>> splitTo,
            Runnable back
    ) {
        return new Wrapper<>(Well.class).accept(layout2 -> {
            layout2.setSize(430, 150);
            layout2.setLayout(null);
            layout2.add(new Wrapper<>(LinearLayout.class).accept(c2 -> {
                c2.setOrientation(LinearLayout.Orientation.VERTICAL);
                c2.setGravityX(LinearLayout.GravityX.LEFT);
                c2.setLocation(25, 25);
                c2.setSize(430, 300);
                final EditText[] csv = new EditText[1];
                c2.addAll(
                        new TextView("Path to CSV table"),
                        new Wrapper<>(LinearLayout.class).accept(l1 -> {
                            l1.setOrientation(LinearLayout.Orientation.HORIZONTAL);
                            l1.setGravity(LinearLayout.GravityX.LEFT, LinearLayout.GravityY.CENTER);
                            EditText editTextCsv = new EditText();
                            csv[0] = editTextCsv;
                            editTextCsv.setSize(300, 30);
                            l1.addAll(editTextCsv, new Button("Browse", new Button.ThemeRaisedButton())
                                    .setOnClickListener(() -> {
                                        try {
                                            String callResult = browseCsvStrings.call();
                                            if (callResult != null) csv[0].setText(callResult);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }));
                        }),
                        new Wrapper<>(LinearLayout.class).accept(l1 -> {
                            l1.setOrientation(LinearLayout.Orientation.HORIZONTAL);
                            l1.setGravity(LinearLayout.GravityX.RIGHT, LinearLayout.GravityY.CENTER);
                            JPanel offset = new JPanel();
                            offset.setSize(38, 64);
                            offset.setOpaque(false);
                            l1.addAll(
                                    offset,
                                    new Button("To Android XML", new Button.ThemeTealButton()).setOnClickListener(
                                            () -> splitTo.accept(csv[0].getText(), new Pair<>(true, false))),
                                    new Button("To IOS strings", new Button.ThemeTealButton()).setOnClickListener(
                                            () -> splitTo.accept(csv[0].getText(), new Pair<>(false, true))),
                                    new Button("Back").setOnClickListener(back::run)
                            );
                        })
                );
            }));
        });
    }
}
