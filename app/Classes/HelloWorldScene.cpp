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
    mWebsocket = new WebSocket("wss://websocketapisample.herokuapp.com/");
    
    // イベント設定
    mWebsocket->onConnectionOpened = []() {
        log("connection!!!!");
        // do something
    };
    mWebsocket->onMessageReceived = [](std::string message) {
        log("%s",message.c_str());
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

    /////////////////////////////
    // 2. add a menu item with "X" image, which is clicked to quit the program
    //    you may modify it.

    // add a "close" icon to exit the progress. it's an autorelease object
    auto closeItem = MenuItemImage::create(
                                           "CloseNormal.png",
                                           "CloseSelected.png",
                                           CC_CALLBACK_1(HelloWorld::menuCloseCallback, this));
    
    closeItem->setPosition(Vec2(origin.x + visibleSize.width - closeItem->getContentSize().width/2 ,
                                origin.y + closeItem->getContentSize().height/2));

    // create menu, it's an autorelease object
    auto menu = Menu::create(closeItem, NULL);
    menu->setPosition(Vec2::ZERO);
    this->addChild(menu, 1);

    /////////////////////////////
    // 3. add your codes below...

    // add a label shows "Hello World"
    // create and initialize a label
    
    //auto label = Label::createWithTTF("Hello World", "fonts/Marker Felt.ttf", 24);
    
    // position the label on the center of the screen
    //label->setPosition(Vec2(origin.x + visibleSize.width/2, origin.y + visibleSize.height - label->getContentSize().height));

    // add the label as a child to this layer
    //this->addChild(label, 1);

    Size s = Director::getInstance()->getVisibleSize();
    
    //レイヤーを作成　色：RGBA(51,75,112,255)　サイズ：画面サイズの80%
    colorLayer = LayerColor::create(Color4B(0, 255, 0, 255), s.width, s.height);
    Size layerSize = colorLayer->getContentSize();
    colorLayer->setPosition(Vec2(origin.x, origin.y));
    this->addChild(colorLayer);

    // add "HelloWorld" splash screen"
//    auto sprite = Sprite::create("HelloWorld.png");

    // position the sprite on the center of the screen
//    sprite->setPosition(Vec2(visibleSize.width/2 + origin.x, visibleSize.height/2 + origin.y));

    // add the sprite as a child to this layer
//    this->addChild(sprite, 0);

    auto downloader = new ResourceDownloader();
    downloader->download("https://cluburo.blob.core.windows.net/cluburo/radio.mp3", [this](std::string savedFilePath) {
        log("-----------------------");
        log("%s", savedFilePath.c_str());
    });
    
    scheduleUpdate();
    return true;
}

void HelloWorld::update(float dt){
    mTimeCounter += dt;
    if(0 < mTimeCounter && mTimeCounter <= 1){
        colorLayer->setColor(Color3B(0, 255, 0));
    }else if(1 < mTimeCounter && mTimeCounter <= 2){
        colorLayer->setColor(Color3B(255, 0, 0));
    }else if(2 < mTimeCounter && mTimeCounter <= 3){
        colorLayer->setColor(Color3B(0, 0, 255));
    }else{
        mTimeCounter = 0;
    }
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
