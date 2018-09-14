$(function () {
    var loading = false;
    // 分页允许返回的最大条数，超过此数则禁止访问后台
    var maxItems = 20;
    // 默认一页返回的商品数
    var pageSize = 10;

    // 列出商品列表的 URL
    var listUrl = '/o2o/frontend/listproductsbyshop';

    // 默认的页码
    var pageNum = 1;
    // 从地址栏获取 shopId
    var shopId = getQueryString('shopId');
    var productCategoryId = '';
    var productName = '';

    // 获取本店铺信息以及商品类别信息列表的 URL
    var searchDivUrl = '/o2o/frontend/listshopdetailpageinfo?shopId='
        + shopId;
    // 渲染出店铺基本信息以及商品类别列表以供搜索
    getSearchDivData();
    // 预先加载 10 条商品信息
    addItems(pageSize, pageNum);
    // 给兑换礼品的 a 标签赋值兑换礼品的 URL， 2.0 讲解
    $("#exhangelist").attr("href", "/o2o/frontend/awardlist?shopId=" + shopId);

    // 获取本店铺信息以及商品类别信息列表
    function getSearchDivData() {
        var url = searchDivUrl;
        $.getJSON(url, function (data) {
            if (data.success) {
                var shop = data.shop;
                $('#shop-cover-pic').attr('src', shop.shopImg);
                $('#shop-update-time').html(
                    new Date(shop.lastEditTime)
                        .Format("yyyy-MM-dd"));
                $('#shop-name').html(shop.shopName);
                $('#shop-desc').html(shop.shopDesc);
                $('#shop-addr').html(shop.shopAddr);
                $('#shop-phone').html(shop.phone);

                var productCategoryList = data.productCategoryList;
                var html = '';
                productCategoryList
                    .map(function (item, index) {
                        html += '<a href="#" class="button" data-product-search-id='
                            + item.productCategoryId
                            + '>'
                            + item.productCategoryName
                            + '</a>';
                    });
                $('#shopdetail-button-div').html(html);
            }
        });
    }

    function addItems(pageSize, pageIndex) {
        // 生成新条目的HTML
        // var url = listUrl + '?' + 'pageIndex=' + pageIndex + '&pageSize='
        //     + pageSize + '&productCategoryId=' + productCategoryId
        //     + '&productName=' + productName + '&shopId=' + shopId;
        var params = {
            pageIndex: pageIndex,
            pageSize: pageSize,
            dataCondition: {
                productName: productName,
                productCategory: {
                    productCategoryId: productCategoryId,
                },
                shop: {
                    shopId: shopId
                }
            }
        };

        loading = true;
        $.ajax({
            url: listUrl,
            type: "POST",
            data: JSON.stringify(params),
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            success: function (data) {
                if (data.success) {
                    maxItems = data.count;
                    var html = '';
                    data.productList.map(function (item, index) {
                        html += '' + '<div class="card" data-product-id='
                            + item.productId + '>'
                            + '<div class="card-header">' + item.productName
                            + '</div>' + '<div class="card-content">'
                            + '<div class="list-block media-list">' + '<ul>'
                            + '<li class="item-content">'
                            + '<div class="item-media">' + '<img src="'
                            + item.imgAddr + '" width="44">' + '</div>'
                            + '<div class="item-inner">'
                            + '<div class="item-subtitle">' + item.productDesc
                            + '</div>' + '</div>' + '</li>' + '</ul>'
                            + '</div>' + '</div>' + '<div class="card-footer">'
                            + '<p class="color-gray">'
                            + new Date(item.lastEditTime).Format("yyyy-MM-dd")
                            + '更新</p>' + '<span>点击查看</span>' + '</div>'
                            + '</div>';
                    });
                    // 将卡片集合添加到目标 HTML 组件里
                    $('.list-div').append(html);
                    // 获取目前为止已显示的卡片总数，包含之前已经加载的
                    var total = $('.list-div .card').length;
                    // 若总数达到跟按照此查询条件列出来的总数一致，则停止后台的加载
                    if (total >= maxItems) {
                        // 加载完毕，则注销无限加载事件，以防不必要的加载
                        $.detachInfiniteScroll($('.infinite-scroll'));
                        // 删除加载提示符
                        $('.infinite-scroll-preloader').remove();
                    }
                    // 否则页码加 1，继续 load 出新的店铺
                    pageNum += 1;
                    // 加载结束，可以再次加载了
                    loading = false;
                    // 刷新页面，显示新加载的店铺
                    $.refreshScroller();
                }
            }
        });
        // $.getJSON(listUrl, JSON.stringify(params), function (data) {
        //     if (data.success) {
        //         maxItems = data.count;
        //         var html = '';
        //         data.productList.map(function (item, index) {
        //             html += '' + '<div class="card" data-product-id='
        //                 + item.productId + '>'
        //                 + '<div class="card-header">' + item.productName
        //                 + '</div>' + '<div class="card-content">'
        //                 + '<div class="list-block media-list">' + '<ul>'
        //                 + '<li class="item-content">'
        //                 + '<div class="item-media">' + '<img src="'
        //                 + item.imgAddr + '" width="44">' + '</div>'
        //                 + '<div class="item-inner">'
        //                 + '<div class="item-subtitle">' + item.productDesc
        //                 + '</div>' + '</div>' + '</li>' + '</ul>'
        //                 + '</div>' + '</div>' + '<div class="card-footer">'
        //                 + '<p class="color-gray">'
        //                 + new Date(item.lastEditTime).Format("yyyy-MM-dd")
        //                 + '更新</p>' + '<span>点击查看</span>' + '</div>'
        //                 + '</div>';
        //         });
        //         // 将卡片集合添加到目标 HTML 组件里
        //         $('.list-div').append(html);
        //         // 获取目前为止已显示的卡片总数，包含之前已经加载的
        //         var total = $('.list-div .card').length;
        //         // 若总数达到跟按照此查询条件列出来的总数一致，则停止后台的加载
        //         if (total >= maxItems) {
        //             // 加载完毕，则注销无限加载事件，以防不必要的加载
        //             $.detachInfiniteScroll($('.infinite-scroll'));
        //             // 删除加载提示符
        //             $('.infinite-scroll-preloader').remove();
        //         }
        //         // 否则页码加 1，继续 load 出新的店铺
        //         pageNum += 1;
        //         // 加载结束，可以再次加载了
        //         loading = false;
        //         // 刷新页面，显示新加载的店铺
        //         $.refreshScroller();
        //     }
        // });
    }

    $(document).on('infinite', '.infinite-scroll-bottom', function () {
        if (loading)
            return;
        addItems(pageSize, pageNum);
    });

    $('#shopdetail-button-div').on(
        'click',
        '.button',
        function (e) {
            productCategoryId = e.target.dataset.productSearchId;
            if (productCategoryId) {
                if ($(e.target).hasClass('button-fill')) {
                    $(e.target).removeClass('button-fill');
                    productCategoryId = '';
                } else {
                    $(e.target).addClass('button-fill').siblings()
                        .removeClass('button-fill');
                }
                $('.list-div').empty();
                pageNum = 1;
                addItems(pageSize, pageNum);
            }
        });

    // 点击商品的卡片进入该商品的详情页
    $('.list-div').on(
        'click',
        '.card',
        function (e) {
            var productId = e.currentTarget.dataset.productId;
            window.location.href = '/o2o/frontend/productdetail?productId='
                + productId;
        });

    // 需要查询的商品名字发生变化后，重置页码，清空原先的商品列表，按照新的名字去查询
    $('#search').on('change', function (e) {
        productName = e.target.value;
        $('.list-div').empty();
        pageNum = 1;
        addItems(pageSize, pageNum);
    });

    // 点击后打开右侧栏
    $('#me').click(function () {
        $.openPanel('#panel-left-demo');
    });
    $.init();
});
