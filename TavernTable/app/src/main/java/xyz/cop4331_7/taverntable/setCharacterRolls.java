package xyz.cop4331_7.taverntable;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class setCharacterRolls extends AppCompatActivity
{
    // Variable declarations
    private TextView tvNum1, tvNum2, tvNum3, tvNum4, tvNum5, tvNum6;
    private TextView tvSTR, tvDEX, tvINT, tvWIS, tvCHA, tvCON;
    String roll1, roll2, roll3, roll4, roll5, roll6;
    String roll1Num, roll2Num, roll3Num, roll4Num, roll5Num, roll6Num;
    private Button bSubmit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_character_rolls);

        // Get the previous intent
        Intent intent = getIntent();

        // Save the rolls from the previous screen onto a String to use later.
        roll1 = intent.getStringExtra("roll1");
        roll2 = intent.getStringExtra("roll2");
        roll3 = intent.getStringExtra("roll3");
        roll4 = intent.getStringExtra("roll4");
        roll5 = intent.getStringExtra("roll5");
        roll6 = intent.getStringExtra("roll6");

        // Get TextView id's
        tvNum1 = (TextView) findViewById(R.id.tDrag1);
        tvNum2 = (TextView) findViewById(R.id.tDrag2);
        tvNum3 = (TextView) findViewById(R.id.tDrag3);
        tvNum4 = (TextView) findViewById(R.id.tDrag4);
        tvNum5 = (TextView) findViewById(R.id.tDrag5);
        tvNum6 = (TextView) findViewById(R.id.tDrag6);

        tvSTR = (TextView) findViewById(R.id.tvSTR);
        tvDEX = (TextView) findViewById(R.id.tvDEX);
        tvINT = (TextView) findViewById(R.id.tvINT);
        tvWIS = (TextView) findViewById(R.id.tvWIS);
        tvCHA = (TextView) findViewById(R.id.tvCHA);
        tvCON = (TextView) findViewById(R.id.tvCON);

        // Set TextView to the roll numbers
        tvNum1.setText(roll1);
        tvNum2.setText(roll2);
        tvNum3.setText(roll3);
        tvNum4.setText(roll4);
        tvNum5.setText(roll5);
        tvNum6.setText(roll6);

        // Set TextView to be draggable
        tvNum1.setOnTouchListener(onTouch);
        tvNum2.setOnTouchListener(onTouch);
        tvNum3.setOnTouchListener(onTouch);
        tvNum4.setOnTouchListener(onTouch);
        tvNum5.setOnTouchListener(onTouch);
        tvNum6.setOnTouchListener(onTouch);

        // Targets to be dropped on
        tvSTR.setOnDragListener(dragListener);
        tvDEX.setOnDragListener(dragListener);
        tvINT.setOnDragListener(dragListener);
        tvWIS.setOnDragListener(dragListener);
        tvCHA.setOnDragListener(dragListener);
        tvCON.setOnDragListener(dragListener);


        roll1Num = tvNum1.getText().toString();

    }

    View.OnTouchListener onTouch = new View.OnTouchListener()
    {

        @Override
        public boolean onTouch(View v, MotionEvent mEvent)
        {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuild = new View.DragShadowBuilder(v);
            v.startDrag(data, shadowBuild, v, 0);
            return true;
        }
    };


    View.OnDragListener dragListener = new View.OnDragListener()
    {

        @Override
        public boolean onDrag(View v, DragEvent event)
        {
            if(event.getAction() == DragEvent.ACTION_DROP)
            {
                //handle the dragged view being dropped over a target view
                TextView dropped = (TextView)event.getLocalState();
                TextView dropTarget = (TextView) v;
                //stop displaying the view where it was before it was dragged
                dropped.setVisibility(View.INVISIBLE);

                //if an item has already been dropped here, there will be different string
                String text = dropTarget.getText().toString();
                //if there is already an item here, set it back visible in its original place
                if(text.equals(tvNum1.getText().toString())) tvNum1.setVisibility(View.VISIBLE);
                else if(text.equals(tvNum2.getText().toString())) tvNum2.setVisibility(View.VISIBLE);
                else if(text.equals(tvNum3.getText().toString())) tvNum3.setVisibility(View.VISIBLE);
                else if(text.equals(tvNum4.getText().toString())) tvNum4.setVisibility(View.VISIBLE);
                else if(text.equals(tvNum5.getText().toString())) tvNum5.setVisibility(View.VISIBLE);
                else if(text.equals(tvNum6.getText().toString())) tvNum6.setVisibility(View.VISIBLE);

                //update the text and color in the target view to reflect the data being dropped
                //if(dropTarget.equals())

                dropTarget.setText(dropped.getText());
                //dropTarget.setBackgroundColor(Color.BLUE);
            }

            /*int dragEvent = event.getAction();
            final View view = (View) event.getLocalState();

            switch(dragEvent)
            {
                case DragEvent.ACTION_DRAG_STARTED:
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    break;
                case DragEvent.ACTION_DROP: {
                    TextView dropped = (TextView) event.getLocalState();
                    TextView dropTarget = (TextView) v;

                    dropped.setVisibility(View.INVISIBLE);

                    String text = dropTarget.getText().toString();

                    if (text.equals(tvNum1.getText().toString()))
                        tvNum1.setVisibility(View.VISIBLE);
                    else if (text.equals(tvNum2.getText().toString()))
                        tvNum1.setVisibility(View.VISIBLE);
                    else if (text.equals(tvNum3.getText().toString()))
                        tvNum1.setVisibility(View.VISIBLE);

                    dropTarget.setText(dropped.getText());
                    dropTarget.setBackgroundColor(Color.BLUE);
                }
                    if(view.getId() == R.id.tDrag2 && view.getId() == R.id.tvSTR)
                    {
                        String a = tvNum2.getText().toString();
                        String b = tvSTR.getText().toString();

                        a = a + b;
                        b = a.substring(0, (a.length() - b.length()));
                        a = a.substring(b.length());

                        tvSTR.setText(a);
                        tvNum2.setText(b);
                    }



                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    break;
                default:
                    break;




            }*/

            System.out.println("INT " + tvINT);

            return true;
        }
    };
}
