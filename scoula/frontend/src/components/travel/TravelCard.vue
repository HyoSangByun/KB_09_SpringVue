<template>
  <div class="card" style="width: 100%">
    <router-link
      :to="{
        name: 'travel/detail',
        params: { no: travel.no },
        query: cr.query,
      }"
    >
      <!-- 이미지: travel.images가 있고 첫 번째가 있을 때만 출력 -->
      <img
        v-if="travel.images && travel.images.length"
        class="card-img-top"
        :src="travel.images[0].url"
        :alt="travel.title"
      />
    </router-link>

    <div class="card-body">
      <h5 class="card-title">
        <router-link
          :to="{
            name: 'travel/detail',
            params: { no: travel.no },
            query: cr.query,
          }"
        >
          {{ travel.title }}
        </router-link>
      </h5>
      <p class="card-text">
        {{ travel.description ? travel.description.substring(0, 250) : "" }}
      </p>
    </div>
  </div>
</template>

<script setup>
import { useRoute } from "vue-router";

const cr = useRoute();

const props = defineProps({
  travel: { type: Object, required: true }, // Type → type 으로 수정
});
</script>

<style>
.card-img-top {
  height: 200px; /* 일정한 높이 유지 */
  object-fit: cover; /* 이미지 비율 유지하며 크롭 */
}

.card-text {
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2; /* 크롬, 사파리 */
  line-clamp: 2; /* 표준 */
  -webkit-box-orient: vertical;
}

a {
  text-decoration: none; /* 링크 밑줄 제거 */
}
</style>
