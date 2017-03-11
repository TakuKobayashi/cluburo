//
//  SoundPlayer.hpp
//  cluburo
//
//  Created by TakuKobayshi on H29/03/07.
//
//

#ifndef SoundPlayer_hpp
#define SoundPlayer_hpp

#include "cocos2d.h"
#include "audio/include/AudioEngine.h"

class SoundPlayer : public cocos2d::Ref{
private:
    SoundPlayer(std::string filePath);
    int mSoundId;
    std::string mFilename;
    bool mPlaying = false;
    bool mLoop = false;
    float mVolume = 1.0;
public:
    static SoundPlayer* create(std::string filePath);
    
    void load();
    void play();
    void pause();
    void stop();
    int getSoundId();
    float getCurrentTime ();
    float getDuration();
    void setVolume (float volume);
    float getVolume ();
    void setLoop (bool loop);
    bool isLoop ();
    bool isPlaying();
    ~SoundPlayer();
};

#endif /* SoundPlayer_hpp */
