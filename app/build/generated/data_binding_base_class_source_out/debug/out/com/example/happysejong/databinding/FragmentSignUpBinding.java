// Generated by view binder compiler. Do not edit!
package com.example.happysejong.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.happysejong.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentSignUpBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageView imageView;

  @NonNull
  public final EditText registerConfirmEditText;

  @NonNull
  public final TextView registerDormitoryTextView;

  @NonNull
  public final EditText registerEmailEditText;

  @NonNull
  public final EditText registerPasswordEditText;

  @NonNull
  public final Button selectDormitoryButton;

  @NonNull
  public final Button signUpButton;

  @NonNull
  public final EditText textView;

  private FragmentSignUpBinding(@NonNull ConstraintLayout rootView, @NonNull ImageView imageView,
      @NonNull EditText registerConfirmEditText, @NonNull TextView registerDormitoryTextView,
      @NonNull EditText registerEmailEditText, @NonNull EditText registerPasswordEditText,
      @NonNull Button selectDormitoryButton, @NonNull Button signUpButton,
      @NonNull EditText textView) {
    this.rootView = rootView;
    this.imageView = imageView;
    this.registerConfirmEditText = registerConfirmEditText;
    this.registerDormitoryTextView = registerDormitoryTextView;
    this.registerEmailEditText = registerEmailEditText;
    this.registerPasswordEditText = registerPasswordEditText;
    this.selectDormitoryButton = selectDormitoryButton;
    this.signUpButton = signUpButton;
    this.textView = textView;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentSignUpBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentSignUpBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_sign_up, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentSignUpBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.imageView;
      ImageView imageView = ViewBindings.findChildViewById(rootView, id);
      if (imageView == null) {
        break missingId;
      }

      id = R.id.registerConfirmEditText;
      EditText registerConfirmEditText = ViewBindings.findChildViewById(rootView, id);
      if (registerConfirmEditText == null) {
        break missingId;
      }

      id = R.id.register_dormitory_textView;
      TextView registerDormitoryTextView = ViewBindings.findChildViewById(rootView, id);
      if (registerDormitoryTextView == null) {
        break missingId;
      }

      id = R.id.registerEmailEditText;
      EditText registerEmailEditText = ViewBindings.findChildViewById(rootView, id);
      if (registerEmailEditText == null) {
        break missingId;
      }

      id = R.id.registerPasswordEditText;
      EditText registerPasswordEditText = ViewBindings.findChildViewById(rootView, id);
      if (registerPasswordEditText == null) {
        break missingId;
      }

      id = R.id.select_dormitory_button;
      Button selectDormitoryButton = ViewBindings.findChildViewById(rootView, id);
      if (selectDormitoryButton == null) {
        break missingId;
      }

      id = R.id.signUpButton;
      Button signUpButton = ViewBindings.findChildViewById(rootView, id);
      if (signUpButton == null) {
        break missingId;
      }

      id = R.id.textView;
      EditText textView = ViewBindings.findChildViewById(rootView, id);
      if (textView == null) {
        break missingId;
      }

      return new FragmentSignUpBinding((ConstraintLayout) rootView, imageView,
          registerConfirmEditText, registerDormitoryTextView, registerEmailEditText,
          registerPasswordEditText, selectDormitoryButton, signUpButton, textView);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
