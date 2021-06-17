package com.example.testcoroutine.ApiClient.Api;

import com.example.testcoroutine.ApiClient.Model.QuarkModel.SuggestionView;
import com.example.testcoroutine.ApiClient.Model.ResultContainer;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface UserApi {

    @GET("/api/quark_sug?q=bai&dn=39180605586-ba002b8d&nt=5&ve=5.1.1.181&pf=3300&fr=android&gi=bTkwBDws55tu9yc6HIw0q%2FHBXbDU&bi=35825&pr=ucpro&sv=release&ds=AAOf1MHjDv/goPFnBA9SE63IoPC52VbSaXfjZlvb2ZS6CQ&di=fecde53aca53953f&ch=kk@store&ut=AAOf1MHjDv%2FgoPFnBA9SE63IoPC52VbSaXfjZlvb2ZS6CQ%3D%3D&uc_param_str=dnntvepffrgibijbprsvdsdichmeut&client_session_id=d1d5bb9a30fc47dd8419c78bd30c1293")
    Single<ResultContainer<SuggestionView>> getQuarkSuggestion();


}
