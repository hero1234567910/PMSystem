<template>
  <div class="share-dropdown-menu">
    <el-tree :data="data" 
			:props="defaultProps" 
			node-key="nodeKey"
			ref="tree2"
			show-checkbox ="showCheckBox"
			check-strictly="checkStrictly"
		>
	  </el-tree>
  </div>
</template>

<script>
export default {
  props: ['nodeKey','data','showCheckBox','checkStrictly'],
  data() {
    return {
      defaultProps:{children:'children',label:'label'},
    }
  },
  methods: {
    
  }
}
</script>

<style lang="scss" >


</style>
