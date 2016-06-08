package com.example.c.myapplication111111;

//Copyright 2013 Google Inc.
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//http://www.apache.org/licenses/LICENSE-2.0
//
//Unless required by applicable law or agreed to in writing, software
//distributed under the License is distributed on an "AS IS" BASIS,
//WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//See the License for the specific language governing permissions and
//limitations under the License.

import com.eclipsesource.v8.V8;
import com.example.c.myapplication111111.HtmlView;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.w3c.dom.DOMException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class HtmlDemo extends Activity {

  HtmlView htmlView;
  LinearLayout linearLayout;
  ScrollView scrollView;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    scrollView = new ScrollView(this);
    linearLayout = new LinearLayout(this);
    linearLayout.setOrientation(LinearLayout.VERTICAL);
    htmlView = new HtmlView(this);
    linearLayout.addView(htmlView);
    scrollView.addView(linearLayout);
    scrollView.setBackgroundColor(0x0ffffffff);
    setContentView(scrollView);
    loadDemo();

    





    V8 runtime = V8.createV8Runtime();

    DocumentBuilderFactory factory =
            DocumentBuilderFactory.newInstance();

    DocumentBuilder builder =
            null;
    try {
      builder = factory.newDocumentBuilder();
    } catch (ParserConfigurationException e) {
      e.printStackTrace();
    }
    Document document = builder.newDocument();
      // Create from whole cloth
      Element root =
              (Element)
                      document.createElement("script");

    runtime.executeScript(//"var tag = document.createElement('script');\n" +
          //  "\n" +
         //   "tag.src = \"https://www.youtube.com/iframe_api\";\n" +
         //   "var firstScriptTag = document.getElementsByTagName('script')[0];\n" +
          //  "firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);\n" +
         //   "\n" +
         //   "// 3. This function creates an <iframe> (and YouTube player)\n" +
         //   "//    after the API code downloads.\n" +
          //  "var player;\n" +
         //   "\n" +
            "function onYouTubeIframeAPIReady() {\n" +
            "    player = new YT.Player('player', {\n" +
            "        height: '390',\n" +
            "        width: '640',\n" +
            "        videoId: 'l-gQLqv9f4o',\n" +
            "        events: {\n" +
            "            'onReady': onPlayerReady,\n" +
            "                'onStateChange': onPlayerStateChange\n" +
            "        }\n" +
            "    });\n" +
            "}\n" +
            "\n" +
            "// 4. The API will call this function when the video player is ready.\n" +
            "function onPlayerReady(event) {\n" +
            "    event.target.playVideo();\n" +
            "}\n" +
            "\n" +
            "// 5. The API calls this function when the player's state changes.\n" +
            "//    The function indicates that when playing a video (state=1),\n" +
            "//    the player should play for six seconds and then stop.\n" +
            "var done = false;\n" +
            "\n" +
            "function onPlayerStateChange(event) {\n" +
            "    if (event.data == YT.PlayerState.PLAYING && !done) {\n" +
            "        setTimeout(stopVideo, 6000);\n" +
            "        done = true;\n" +
            "    }\n" +
            "}\n" +
            "\n" +
            "function stopVideo() {\n" +
            "    player.stopVideo();\n" +
            "}\n");
   // int result = runtime.executeIntegerScript(""
     //       + "var hello = 'hello, ';\n"
      //      + "var world = 'world!';\n"
      //      + "hello.concat(world).length();\n");
    //System.out.println(result);
    runtime.release();
  }

  @Override
  public void onBackPressed() {
    if (htmlView.getBaseUrl().equals(HtmlView.ASSET_BASE_URL.resolve("index.html"))) {
      super.onBackPressed();
    } else {
      loadDemo();
    }
  }
  
  void loadDemo() {
    htmlView.loadUrl("index.html");
    while(linearLayout.getChildCount() > 1) {
    	linearLayout.removeViewAt(linearLayout.getChildCount() - 1);
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    menu.add("Reset");
    menu.add("Add 10 WebViews");
    menu.add("Add 10 HtmlViews");
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    String title = item.getTitle().toString();
    if (title.equals("Reset")) {
      loadDemo();
      return true;
    }
    TextView label = new TextView(this);
    label.setText("View count: " + (linearLayout.getChildCount() + 10));
    if (title.equals("Add 10 HtmlViews")) {
      for (int i = 0; i < 10; i++) {
        HtmlView htmlView = new HtmlView(this);
        linearLayout.addView(htmlView);
        htmlView.loadUrl("index.html");
      }
      linearLayout.addView(label);
      return true;
    }
    if (title.equals("Add 10 WebViews")) {
      for (int i = 0; i < 10; i++) {
       // WebView webView = new WebView(this);
       // linearLayout.addView(webView);
       // webView.loadUrl("file:///android_asset/index.html");
      }
      linearLayout.addView(label);
      return true;
    }
   	return super.onContextItemSelected(item);
  }
}
