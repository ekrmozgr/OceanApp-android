package com.example.ocean1;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class addProductFragment extends Fragment {

    homepageActivity homepageActivity;
    Context ctx;
    private ImageView productIcon;
    String sImage;
    private Uri image_url;
    TextView tcategory;
    TextView tcourselevel;
    HashMap<String,Integer> categories;
    HashMap<String,Integer> courselevels;
    int categoryCheckedItem;
    int courseCheckedItem;
    Button addProduct;
    EditText ttitle;
    EditText tdescription;
    EditText tcourseHour;
    EditText tcourseMinute;
    EditText tcompanyName;
    EditText tcompanyWebsite;
    EditText tprice;
    EditText tdiscountpercent;
    String[] categoryKeys;
    String[] courseLevelKeys;

    private static final int IMAGE_PICK_GALLERY_CODE=400;


    public void Olumlu_Uyarı() {
        Dialog dialog = new Dialog(homepageActivity);
        dialog.setContentView(R.layout.productaddcomplete);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView imageViewClose = dialog.findViewById(R.id.imageViewKapat);
        AppCompatButton button_okey = dialog.findViewById(R.id.button_okey);


        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                addProductFragment addProductFragment = new addProductFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.container, addProductFragment).commit();
            }
        });

        button_okey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                addProductFragment addProductFragment = new addProductFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.container, addProductFragment).commit();
            }
        });

        dialog.show();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_addproduct, container, false);

        homepageActivity=(homepageActivity) getActivity();
        ctx = homepageActivity.getApplicationContext();
        productIcon = view.findViewById(R.id.productIcon);
        tcategory = view.findViewById(R.id.category);
        tcategory.setClickable(false);
        tcourselevel = view.findViewById(R.id.courseLevel);
        tcourselevel.setClickable(false);
        addProduct = view.findViewById(R.id.addProduct);
        ttitle = view.findViewById(R.id.title);
        tdescription = view.findViewById(R.id.description);
        tcourseHour = view.findViewById(R.id.courseHourDuration);
        tcourseMinute = view.findViewById(R.id.courseMinuteDuration);
        tcompanyName = view.findViewById(R.id.CompanyName);
        tcompanyWebsite = view.findViewById(R.id.CompanyWebsite);
        tprice = view.findViewById(R.id.finalPrice);
        tdiscountpercent = view.findViewById(R.id.discountPercent);


        categories = new HashMap<>();
        Api.getCategories(categories, ctx, new VolleyCallBack() {
            @Override
            public void onSuccess() {
                tcategory.setClickable(true);
            }
        });

        courselevels = new HashMap<>();
        Api.getCourseLevels(courselevels, ctx, new VolleyCallBack(){
           @Override
           public void onSuccess(){
                tcourselevel.setClickable(true);
           }
        });

        categoryCheckedItem = -1;
        tcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(homepageActivity);
                mBuilder.setTitle("Choose a Category");
                mBuilder.setIcon(R.drawable.list_icon);
                categoryKeys = new String[categories.size()];
                int index = 0;
                for (Map.Entry<String, Integer> mapEntry : categories.entrySet()) {
                    categoryKeys [index] = mapEntry.getKey();
                    index++;
                }

                mBuilder.setSingleChoiceItems(categoryKeys, categoryCheckedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        categoryCheckedItem = i;
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.setCancelable(false);
                mDialog.show();
            }
        });

        courseCheckedItem = -1;
        tcourselevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(homepageActivity);
                mBuilder.setTitle("Choose a Course Level");
                mBuilder.setIcon(R.drawable.list_icon);
                courseLevelKeys = new String[courselevels.size()];
                int index = 0;
                for (Map.Entry<String, Integer> mapEntry : courselevels.entrySet()) {
                    courseLevelKeys[index] = mapEntry.getKey();
                    index++;
                }

                mBuilder.setSingleChoiceItems(courseLevelKeys, courseCheckedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        courseCheckedItem = i;
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.setCancelable(false);
                mDialog.show();
            }
        });

        productIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImagePickDialog();
            }
        });

        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject jsonBody = new JSONObject();
                try {
                    int userId = Api.user.id;
                    String name = ttitle.getText().toString();
                    double price = Double.parseDouble(tprice.getText().toString());
                    int discountPercent = Integer.parseInt(tdiscountpercent.getText().toString());
                    String explanation  = tdescription.getText().toString();
                    String courseHourDuration = tcourseHour.getText().toString();
                    String courseMinuteDuration = tcourseMinute.getText().toString();
                    int productCategoryId = categories.get(categoryKeys[categoryCheckedItem]);
                    int courseLevelId = courselevels.get(courseLevelKeys[courseCheckedItem]);
                    String companyName = tcompanyName.getText().toString();
                    String companyWebsite = tcompanyWebsite.getText().toString();
                    String base64Image = sImage;

                    jsonBody.put("userId", userId);
                    jsonBody.put("name",name);
                    jsonBody.put("price",price);
                    jsonBody.put("discountPercent",discountPercent);
                    jsonBody.put("explanation",explanation);
                    jsonBody.put("courseHourDuration",courseHourDuration);
                    jsonBody.put("courseMinuteDuration",courseMinuteDuration);
                    jsonBody.put("productCategoryId",productCategoryId);
                    jsonBody.put("courseLevelId",courseLevelId);
                    jsonBody.put("companyName",companyName);
                    jsonBody.put("companyWebsite",companyWebsite);
                    jsonBody.put("base64Image",base64Image);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Api.addProduct(jsonBody, ctx, new VolleyCallBack() {
                    @Override
                    public void onSuccess() {
                        Olumlu_Uyarı();
                    }
                });
            }
        });

        return view;
    }

    private void showImagePickDialog() {
        AlertDialog.Builder builder= new AlertDialog.Builder(homepageActivity);
        String[] options={"Gallery"};
        builder.setTitle("Pick Image").setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                pickFromGallery();
            }
        }).show();
    }

    private void pickFromGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/+");
        startActivityForResult(intent,IMAGE_PICK_GALLERY_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        if(data == null)
            return;
        image_url=data.getData();
        productIcon.setImageURI(image_url);
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(homepageActivity.getContentResolver(),image_url);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
            byte[] bytes=stream.toByteArray();
            sImage= Base64.encodeToString(bytes,Base64.DEFAULT);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
