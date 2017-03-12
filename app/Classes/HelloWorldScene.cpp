#include "HelloWorldScene.h"
#include "SimpleAudioEngine.h"

USING_NS_CC;

Scene* HelloWorld::createScene()
{
    // 'scene' is an autorelease object
    auto scene = Scene::create();
    
    // 'layer' is an autorelease object
    auto layer = HelloWorld::create();

    // add layer as a child to scene
    scene->addChild(layer);

    // return the scene
    return scene;
}

// on "init" you need to initialize your instance
bool HelloWorld::init()
{
    
    // インスタンス生成
    mWebsocket = new WebSocket("ws://40.74.133.145:3000/");
    
    // イベント設定
    mWebsocket->onConnectionOpened = []() {
        log("connection!!!!");
        // do something
    };
    mWebsocket->onMessageReceived = [](std::string message) {
        log("%s",message.c_str());
        rapidjson::Document document;
        if (document.Parse(message.c_str()).HasParseError()) {
            log("parse error!!");
        }
        std::string str = "play_sound";
        log("%s", document["action"].GetString());
        if(document["action"].GetString() == str){
            std::string url = "https://cluburo.blob.core.windows.net/cluburo/";
            auto downloader = new ResourceDownloader();
            downloader->download(url.append(document["filepath"].GetString()));
        }
        // do something
    };
    mWebsocket->onConnectionClosed = []() {
        log("closed!!!!");
        // do something
    };
    
    // 接続開始
    mWebsocket->connect();
    
    //////////////////////////////
    // 1. super init first
    if ( !Layer::init() )
    {
        return false;
    }
    
    auto visibleSize = Director::getInstance()->getVisibleSize();
    Vec2 origin = Director::getInstance()->getVisibleOrigin();

    Size s = Director::getInstance()->getVisibleSize();
    //レイヤーを作成　色：RGBA(51,75,112,255)　サイズ：画面サイズの80%
    colorLayer = LayerColor::create(Color4B(0, 0, 0, 255), s.width, s.height);
    Size layerSize = colorLayer->getContentSize();
    colorLayer->setPosition(Vec2(origin.x, origin.y));
    this->addChild(colorLayer);

    scheduleUpdate();
    return true;
}

void HelloWorld::update(float dt){
    mTimeCounter += dt;
    if(mTimeCounter >= 0.5){
        mTimeCounter = mTimeCounter - 1;
    }
    float graColor = std::abs(mTimeCounter);
    colorLayer->setColor(Color3B(255 * graColor / 0.5, 0, 255 * graColor / 0.5));
}

void HelloWorld::menuCloseCallback(Ref* pSender)
{
    //Close the cocos2d-x game scene and quit the application
    Director::getInstance()->end();
    // 接続終了
    mWebsocket->close();

    #if (CC_TARGET_PLATFORM == CC_PLATFORM_IOS)
    exit(0);
#endif
    
    /*To navigate back to native iOS screen(if present) without quitting the application  ,do not use Director::getInstance()->end() and exit(0) as given above,instead trigger a custom event created in RootViewController.mm as below*/
    
    //EventCustom customEndEvent("game_scene_close_event");
    //_eventDispatcher->dispatchEvent(&customEndEvent);
    
    
}
