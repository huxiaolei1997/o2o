$(function () {
    var productId = getQueryString('productId');
    //var shopId = 1; 这里的 shopId 改为从 session 中获取，所以每次访问这个页面的时候必须先访问 /o2o/shopadmin/shopmanagement?shopId=1 这个页面
    // 获取商品详情
    var infoUrl = '/o2o/shopadmin/getproductbyid?productId=' + productId;
    // 获取商品类别列表
    var categoryUrl = '/o2o/shopadmin/getproductcategorylist';
    var productPostUrl = '/o2o/shopadmin/modifyproduct';
    var isEdit = false;
    if (productId) {
        // 若有 productId 则为编辑操作
        getInfo(productId);
        isEdit = true;
    } else {
        // 没有 productId 则为添加操作
        getCategory();
        productPostUrl = '/o2o/shopadmin/addproduct';
    }

    // 获取需要编辑的商品的商品信息，并赋值给表单
    function getInfo(id) {
        $.getJSON(
            infoUrl,
            function (data) {
                if (data.success) {
                    var product = data.product;
                    $('#product-name').val(product.productName);
                    $('#product-desc').val(product.productDesc);
                    $('#priority').val(product.priority);
                    $('#point').val(product.point);
                    $('#normal-price').val(product.normalPrice);
                    $('#promotion-price').val(
                        product.promotionPrice);

                    var optionHtml = '';
                    var optionArr = data.productCategoryList;
                    var optionSelected = product.productCategory.productCategoryId;
                    optionArr
                        .map(function (item, index) {
                            var isSelect = optionSelected === item.productCategoryId ? 'selected' : '';
                            optionHtml += '<option data-value="'
                                + item.productCategoryId
                                + '"'
                                + isSelect
                                + '>'
                                + item.productCategoryName
                                + '</option>';
                        });
                    $('#category').html(optionHtml);
                }
            });
    }

    function getCategory() {
        $.getJSON(categoryUrl, function (response) {
            if (response.success) {
                var productCategoryList = response.data;
                var optionHtml = '';
                productCategoryList.map(function (item, index) {
                    optionHtml += '<option data-value="'
                        + item.productCategoryId + '">'
                        + item.productCategoryName + '</option>';
                });
                $('#category').html(optionHtml);
            }
        });
    }

    $('.detail-img-div').on('change', '.detail-img:last-child', function () {
        if ($('.detail-img').length < 6) {
            $('#detail-img').append('<input type="file" class="detail-img">');
        }
    });

    $('#submit').click(function () {
        var product = {};
        product.productName = $('#product-name').val();
        product.productDesc = $('#product-desc').val();
        product.priority = $('#priority').val();
        product.point = $("#point").val();
        product.normalPrice = $('#normal-price').val();
        product.promotionPrice = $('#promotion-price').val();
        product.productCategory = {
            productCategoryId: $('#category').find('option').not(
                function () {
                    return !this.selected;
                }).data('value')
        };
        product.productId = productId;

        var thumbnail = $('#small-img')[0].files[0];
        console.log(thumbnail);
        var formData = new FormData();
        formData.append('thumbnail', thumbnail);
        $('.detail-img').map(
            function (index, item) {
                if ($('.detail-img')[index].files.length > 0) {
                    formData.append('productImg' + index,
                        $('.detail-img')[index].files[0]);
                }
            });
        formData.append('productStr', JSON.stringify(product));
        var verifyCodeActual = $('#j_captcha').val();
        if (!verifyCodeActual) {
            $.toast('请输入验证码！');
            return;
        }
        formData.append("verifyCodeActual", verifyCodeActual);
        $.ajax({
            url: productPostUrl,
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            cache: false,
            success: function (data) {
                var data = JSON.parse(data);
                if (data.success) {
                    $.toast('提交成功！');
                    $('#captcha_img').click();
                } else {
                    $.toast('提交失败！');
                    $('#captcha_img').click();
                }
            }
        });
    });
});