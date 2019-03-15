package com.rosen.jambo.utils;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by Derick W on 15,March,2019
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
public class Roboto {

    Context context;
    public Roboto(Context context){
        this.context = context;
    }

    // the custom roboto fonts
    public Typeface getRegularRoboto(){
        return Typeface.createFromAsset(context.getAssets(), "fonts/RobotoRegular.ttf");
    }

    public Typeface getBoldRoboto(){
        return Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Bold.ttf");
    }

    public Typeface getLightRoboto(){
        return Typeface.createFromAsset(context.getAssets(), "fonts/RobotoCondensedLight.ttf");
    }

    public Typeface getJournal(){
        return Typeface.createFromAsset(context.getAssets(), "fonts/journal.ttf");
    }
    public Typeface getLobster(){
        return Typeface.createFromAsset(context.getAssets(), "fonts/LobsterTwoRegular.otf");
    }
    public Typeface getKaushan(){
        return Typeface.createFromAsset(context.getAssets(), "fonts/KaushanScriptRegular.otf");
    }
    public Typeface getDancing(){
        return Typeface.createFromAsset(context.getAssets(), "fonts/DancingScriptRegular.otf");
    }
    public Typeface getGoodDog(){
        return Typeface.createFromAsset(context.getAssets(), "fonts/GoodDog.otf");
    }

    public Typeface getColabReg(){
        return Typeface.createFromAsset(context.getAssets(), "fonts/ColabReg.otf");
    }

    public Typeface getExoRegular(){
        return Typeface.createFromAsset(context.getAssets(), "fonts/ExoRegular.otf");
    }

    public Typeface getStencilLight(){
        return Typeface.createFromAsset(context.getAssets(), "fonts/StencilLight.otf");
    }

    public Typeface getTillium(){
        return Typeface.createFromAsset(context.getAssets(), "fonts/Titillium.otf");
    }

    public Typeface getWalkWay(){
        return Typeface.createFromAsset(context.getAssets(), "fonts/Walkway.ttf");
    }
}
