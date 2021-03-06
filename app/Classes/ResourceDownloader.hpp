//
//  ResourceDownloader.hpp
//  cluburo
//
//  Created by TakuKobayshi on H29/03/07.
//
//

#ifndef ResourceDownloader_hpp
#define ResourceDownloader_hpp

#include "cocos2d.h"
#include "network/HttpClient.h"
#include "SoundPlayer.hpp"
#include <fstream>

class ResourceDownloader : public cocos2d::Ref{
private:
    void onHttpRequestCompleted(cocos2d::network::HttpClient* client, cocos2d::network::HttpResponse* response);
public:
    void download(std::string url);
};


#endif /* downloadFile_hpp */
